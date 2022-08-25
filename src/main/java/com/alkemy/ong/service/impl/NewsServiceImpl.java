package com.alkemy.ong.service.impl;


import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.dto.response.NewsPageResponse;
import com.alkemy.ong.exception.NameAlreadyExists;
import com.alkemy.ong.model.News;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.service.NewsService;
import com.alkemy.ong.service.mapper.NewsMapper;
import com.alkemy.ong.util.PaginationUtil;
import javassist.NotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Locale;

@Service
public class NewsServiceImpl extends PaginationUtil<News, Long, NewsRepository> implements NewsService{

    @Autowired private ModelMapper modelMapper;

    @Autowired private NewsRepository newsRepository;

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private MessageSource messageSource;


    @Override
    public NewsDto createNews(NewsDto newsDto) {
        News news = newsMapper.newsDTOtoEntity(newsDto);
        News newNews = newsRepository.save(news);
        return newsMapper.newsEntityToDTO(newNews);
    }


    public NewsDto findNewsById(Long id) throws NameAlreadyExists {
        if (!newsRepository.existsById(id)) throw new NameAlreadyExists(messageSource.getMessage("news.notFound", null, Locale.US));
        return newsMapper.newsEntityToDTO(newsRepository.findById(id).get());
    }

    @Override
    public NewsDto updateNews(NewsDto newsDto, Long id) {
        News news = newsMapper.newsDTOtoEntity(newsDto, id);
        return newsMapper
                .newsEntityToDTO(newsRepository
                        .save(news));
    }

    @Override
    @Transactional
    public void deleteById(Long id){
        News news = newsRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("News "
                        .concat(messageSource.getMessage("not.found", null, Locale.US))));
        if(!news.isDeleted()){
            newsRepository.deleteById(id);
        }
    }
    @Override
    public NewsPageResponse pagination(Integer numberOfPage) throws NotFoundException {
        if (numberOfPage < 1) throw new NotFoundException(messageSource.getMessage("resource.not.found", null, Locale.US));

        Page<News> page = getPage(numberOfPage);
        String previousUrl = urlGetPrevious(numberOfPage);
        String nextUrl = urlGetNext(page, numberOfPage);

        if (page.getTotalPages() < numberOfPage) throw new NotFoundException(messageSource.getMessage("page.without.elements", null, Locale.US));
        return newsMapper.buildPageResponse(page.getContent(), previousUrl, nextUrl);
    }

    private NewsDto toDto(News news){
        return modelMapper.map(news, NewsDto.class);
    }

    private News toEntity(NewsDto newsDto){
        return modelMapper.map(newsDto, News.class);
    }

}