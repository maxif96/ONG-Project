package com.alkemy.ong.service.mapper;

import com.alkemy.ong.dto.NewsDto;
import com.alkemy.ong.dto.response.NewsPageResponse;
import com.alkemy.ong.dto.response.NewsResponseDTO;
import com.alkemy.ong.model.News;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class NewsMapper {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private CategoryRepository categoryRepository;

    public NewsDto newsEntityToDTO (News news) {
        return NewsDto
                .builder()
                .id(news.getId())
                .content(news.getContent())
                .name(news.getName())
                .image(news.getImage())
                .categoryId(news.getCategoryId().getId())
                .createDate(news.getCreateDate())
                .updateDate(news.getUpdateDate())
                .build();
    }

    public News newsDTOtoEntity (NewsDto newsDto) {
        return News
                .builder()
                .content(newsDto.getContent())
                .name(newsDto.getName())
                .image(newsDto.getImage())
                .categoryId(categoryRepository
                        .findById(newsDto.getCategoryId())
                        .orElseThrow(() -> new EntityNotFoundException("Category doesn't found")))
                .createDate(newsDto.getCreateDate())
                .updateDate(new Date())
                .build();
    }

    public News newsDTOtoEntity (NewsDto newsDto, Long id) {
        return News
                .builder()
                .id(id)
                .content(newsDto.getContent())
                .name(newsDto.getName())
                .image(newsDto.getImage())
                .categoryId(categoryRepository
                        .findById(newsDto.getCategoryId())
                        .orElseThrow(() -> new EntityNotFoundException("Category doesn't found.")))
                .createDate(newsDto.getCreateDate())
                .updateDate(new Date())
                .build();
    }

    public NewsResponseDTO entityToResponse (News newEntity){
        return NewsResponseDTO.builder()
                .id(newEntity.getId())
                .name(newEntity.getName())
                .image(newEntity.getImage())
                .content(newEntity.getContent())
                .categoryId(newEntity.getCategoryId().getId())
                .createDate(newEntity.getCreateDate())
                .build();
    }

    public NewsPageResponse buildPageResponse(List<News> members, String previous, String next){
        return NewsPageResponse.builder()
                .news(members.stream()
                        .map(this::entityToResponse)
                        .collect(Collectors.toList()))
                .previous(previous)
                .next(next)
                .build();
    }

}
