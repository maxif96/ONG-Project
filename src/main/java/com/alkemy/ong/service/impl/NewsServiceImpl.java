package com.alkemy.ong.service.impl;


import com.alkemy.ong.dto.NewsRequestDTO;
import com.alkemy.ong.dto.response.CommentResponseDTO;
import com.alkemy.ong.dto.response.NewsPageResponse;
import com.alkemy.ong.dto.response.NewsResponseDTO;
import com.alkemy.ong.model.News;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.service.NewsService;
import com.alkemy.ong.service.mapper.NewsMapper;
import com.alkemy.ong.service.mapper.CommentMapper;
import com.alkemy.ong.util.PaginationUtil;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class NewsServiceImpl extends PaginationUtil<News, Long, NewsRepository> implements NewsService {

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private NewsMapper newsMapper;
    @Autowired
    private MessageSource messageSource;

    @Transactional
    public NewsResponseDTO createNews(NewsRequestDTO newsRequestDTO) {
        News news = newsMapper.DTOToEntity(newsRequestDTO);
        News newSaved = repository.save(news);
        return newsMapper.entityToDTO(newSaved);
    }

    public NewsResponseDTO findNewsById(Long id) {
        News newsFromDB = repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("news.notFound", null, Locale.US)));
        return newsMapper.entityToDTO(newsFromDB);
    }

    @Transactional
    public NewsResponseDTO updateNews(NewsRequestDTO newsRequestDTO, Long id) {
        News newsFromDB = repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("News with that id was not found"));
        News newsToUpdate = newsMapper.updateNews(newsRequestDTO, newsFromDB);

        return newsMapper.entityToDTO(
                repository.save(newsToUpdate)
        );
    }

    @Transactional
    public void deleteById(Long id) {
        if (!repository.existsById(id)) throw new EntityNotFoundException(
                "News ".concat(messageSource.getMessage("not.found", null, Locale.US)));
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public NewsPageResponse pagination(Integer numberOfPage) throws NotFoundException {
        if (numberOfPage < 1)
            throw new NotFoundException(messageSource.getMessage("resource.not.found", null, Locale.US));

        Page<News> page = getPage(numberOfPage);
        String previousUrl = urlGetPrevious(numberOfPage);
        String nextUrl = urlGetNext(page, numberOfPage);

        if (page.getTotalPages() < numberOfPage)
            throw new NotFoundException(messageSource.getMessage("page.without.elements", null, Locale.US));
        return newsMapper.buildPageResponse(page.getContent(), previousUrl, nextUrl);
    }

    public List<CommentResponseDTO> findCommentByNewsId(Long newsId) {
        if (!repository.existsById(newsId)) throw new EntityNotFoundException("News with that id does not exist.");
        return commentRepository.findAllByNewsId(newsId)
                .stream()
                .map(p -> commentMapper.entityToResponseDTO(p))
                .collect(Collectors.toList());
    }


}