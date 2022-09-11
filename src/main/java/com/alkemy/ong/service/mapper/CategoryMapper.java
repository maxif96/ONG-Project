package com.alkemy.ong.service.mapper;

import com.alkemy.ong.dto.CategoryRequestDTO;
import com.alkemy.ong.dto.response.CategoryPageResponse;
import com.alkemy.ong.dto.response.CategoryResponseDTO;
import com.alkemy.ong.model.Category;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CategoryMapper {


    public Category RequestDTOToEntity(CategoryRequestDTO categoryRequestDTO) {
        return Category
                .builder()
                .name(categoryRequestDTO.getName())
                .image(categoryRequestDTO.getImage())
                .description(categoryRequestDTO.getDescription())
                .createDate(LocalDateTime.now())
                .build();
    }

    public CategoryResponseDTO entityToResponseDTO(Category category) {
        return CategoryResponseDTO
                .builder()
                .id(category.getId())
                .name(category.getName())
                .image(category.getImage())
                .description(category.getDescription())
                .updateDate(new Date())
                .build();
    }

    public Category updateCategory(Category category, CategoryRequestDTO newsFields) {
        return Category.builder()
                .id(category.getId())
                .name(newsFields.getName())
                .image(newsFields.getImage())
                .description(newsFields.getDescription())
                .updateDate(LocalDateTime.now())
                .build();
    }

    public CategoryPageResponse buildPageResponse(List<Category> categories, String previous, String next) {
        return CategoryPageResponse.builder()
                .categories(categories.stream()
                        .map(this::entityToResponseDTO)
                        .collect(Collectors.toList()))
                .previous(previous)
                .next(next)
                .build();
    }

}
