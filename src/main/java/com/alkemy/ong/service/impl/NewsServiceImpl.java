package com.alkemy.ong.service.impl;


import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.dto.response.CommentResponseDTO;
import com.alkemy.ong.dto.response.NewsPageResponse;
import com.alkemy.ong.exception.NameAlreadyExists;
import com.alkemy.ong.model.News;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.service.NewsService;
import com.alkemy.ong.service.mapper.NewsMapper;
import com.alkemy.ong.service.mapper.comment.CommentMapper;
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


    @Override
    public NewsDto createNews(NewsDto newsDto) {
        News news = newsMapper.newsDTOtoEntity(newsDto);
        News newSaved = repository.save(news);
        return newsMapper.newsEntityToDTO(newSaved);
    }


    public NewsDto findNewsById(Long id) throws NameAlreadyExists {
        if (!repository.existsById(id)) throw new NameAlreadyExists(messageSource.getMessage("news.notFound", null, Locale.US));
        return newsMapper.newsEntityToDTO(repository.findById(id).get());
    }

    @Override
    public NewsDto updateNews(NewsDto newsDto, Long id) {
        News news = newsMapper.newsDTOtoEntity(newsDto, id);
        return newsMapper
                .newsEntityToDTO(repository
                        .save(news));
    }

    @Transactional
    public void deleteById(Long id){
        News news = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("News "
                        .concat(messageSource.getMessage("not.found", null, Locale.US))));
        if(!news.isDeleted()){
            repository.deleteById(id);
        }
    }
    @Transactional(readOnly = true)
    public NewsPageResponse pagination(Integer numberOfPage) throws NotFoundException {
        if (numberOfPage < 1) throw new NotFoundException(messageSource.getMessage("resource.not.found", null, Locale.US));

        Page<News> page = getPage(numberOfPage);
        String previousUrl = urlGetPrevious(numberOfPage);
        String nextUrl = urlGetNext(page, numberOfPage);

        if (page.getTotalPages() < numberOfPage) throw new NotFoundException(messageSource.getMessage("page.without.elements", null, Locale.US));
        return newsMapper.buildPageResponse(page.getContent(), previousUrl, nextUrl);
    }

    public List<CommentResponseDTO> findCommentByNewsId(Long newsId) throws Exception {
        return commentRepository.findAllByNewsId(newsId)
                .stream()
                .map(p -> commentMapper.entityToResponseDTO(p))
                .collect(Collectors.toList());
    }

    private NewsDto toDto(News news){
        return modelMapper.map(news, NewsDto.class);
    }

    private News toEntity(NewsDto newsDto){
        return modelMapper.map(newsDto, News.class);
    }

}