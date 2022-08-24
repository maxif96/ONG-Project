package com.alkemy.ong.service;

import com.alkemy.ong.dto.CategoryRequestDTO;
import com.alkemy.ong.dto.response.CategoryPageResponse;
import com.alkemy.ong.dto.response.CategoryResponseDTO;
import com.alkemy.ong.exception.EmptyListException;
import javassist.NotFoundException;

import java.util.List;

public interface CategoryService {

    CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) throws Exception;
    CategoryPageResponse getAllCategories(Integer numberOfPage) throws NotFoundException;
    List<String> getCategoryNames() throws EmptyListException;
    CategoryResponseDTO update (Long id, CategoryRequestDTO categoryRequestDTO);
    void deleteCategory(Long id);
}