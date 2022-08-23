
package com.alkemy.ong.service.mapper;

import com.alkemy.ong.dto.CategoryRequestDTO;
import com.alkemy.ong.dto.response.CategoryResponseDTO;
import com.alkemy.ong.model.Category;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

@Component
public class CategoryMapper {
    


    public Category DTOToEntity(CategoryRequestDTO categoryRequestDTO){
        return Category
                .builder()
                .id(categoryRequestDTO.getCategoryId())
                .name(categoryRequestDTO.getName())
                .image(categoryRequestDTO.getImage())
                .description(categoryRequestDTO.getDescription())
                .updateDate(new Date())
                .build();
    }

    public CategoryResponseDTO entityToResponseDTO (Category category){
        return CategoryResponseDTO
                .builder()
                .id(category.getId())
                .name(category.getName())
                .image(category.getImage())
                .description(category.getDescription())
                .updateDate(category.getUpdateDate())
                .build();
    }

    public Category updateCategory (Category category, CategoryRequestDTO newsFields){

        return Category.builder()
                .id(category.getId())
                .name(newsFields.getName())
                .image(newsFields.getImage())
                .description(newsFields.getDescription())
                .updateDate(new Date())
                .build();
    }

    public List<CategoryResponseDTO> entityListToResponseDTO (List<Category> categories){
        List<CategoryResponseDTO> categoryResponseDTOList = new ArrayList<>();
        categories.forEach((p) -> categoryResponseDTOList.add(entityToResponseDTO(p)));
        return categoryResponseDTOList;
    }
}
