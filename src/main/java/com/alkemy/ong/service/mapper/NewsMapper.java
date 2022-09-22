package com.alkemy.ong.service.mapper;

import com.alkemy.ong.dto.NewsRequestDTO;
import com.alkemy.ong.dto.response.NewsPageResponse;
import com.alkemy.ong.dto.response.NewsResponseDTO;
import com.alkemy.ong.model.News;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
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

    public NewsResponseDTO entityToDTO(News news) {
        return NewsResponseDTO
                .builder()
                .id(news.getId())
                .content(news.getContent())
                .name(news.getName())
                .image(news.getImage())
                .categoryId(news.getCategory().getId())
                .createDate(news.getCreateDate())
                .updateDate(news.getUpdateDate())
                .build();
    }

    public News DTOToEntity(NewsRequestDTO newsRequestDTO) {
        return News
                .builder()
                .content(newsRequestDTO.getContent())
                .name(newsRequestDTO.getName())
                .image(newsRequestDTO.getImage())
                .category(categoryRepository
                        .findById(newsRequestDTO.getCategoryId())
                        .orElseThrow(() -> new EntityNotFoundException("Category doesn't found")))
                .build();
    }

    public News updateNews(NewsRequestDTO newsRequestDTO, News newsFromDB) {
        if (newsRequestDTO.getCategoryId() == null) throw new EntityNotFoundException("Category can not be empty.");
        return News
                .builder()
                .id(newsFromDB.getId())
                .content(newsRequestDTO.getContent())
                .name(newsRequestDTO.getName())
                .image(newsRequestDTO.getImage())
                .category(categoryRepository
                        .findById(newsRequestDTO.getCategoryId())
                        .orElseThrow(() -> new EntityNotFoundException("Category was not found.")))
                .createDate(newsFromDB.getCreateDate())
                .updateDate(LocalDateTime.now())
                .build();
    }

    public NewsResponseDTO entityToResponse(News newEntity) {
        return NewsResponseDTO.builder()
                .id(newEntity.getId())
                .name(newEntity.getName())
                .image(newEntity.getImage())
                .content(newEntity.getContent())
                .categoryId(newEntity.getCategory().getId())
                .createDate(newEntity.getCreateDate())
                .build();
    }

    public NewsPageResponse buildPageResponse(List<News> members, String previous, String next) {
        return NewsPageResponse.builder()
                .news(members.stream()
                        .map(this::entityToResponse)
                        .collect(Collectors.toList()))
                .previous(previous)
                .next(next)
                .build();
    }

}
