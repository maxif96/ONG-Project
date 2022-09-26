package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.CategoryRequestDTO;
import com.alkemy.ong.dto.response.CategoryPageResponse;
import com.alkemy.ong.dto.response.CategoryResponseDTO;
import com.alkemy.ong.exception.EmptyListException;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.CategoryService;
import com.alkemy.ong.service.mapper.CategoryMapper;
import com.alkemy.ong.util.PaginationUtil;
import com.amazonaws.services.kms.model.AlreadyExistsException;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Locale;

@Service
public class CategoryServiceImpl extends PaginationUtil<Category, Long, CategoryRepository> implements CategoryService {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CategoryMapper categoryMapper;

    @Transactional
    public CategoryResponseDTO create(CategoryRequestDTO categoryRequestDTO) throws Exception {
        if (repository.existsByName(categoryRequestDTO.getName())) throw new AlreadyExistsException(
                messageSource.getMessage("error.category.already.exists", null, Locale.US));
        Category categoryToSave = categoryMapper.RequestDTOToEntity(categoryRequestDTO);
        return categoryMapper.entityToResponseDTO(repository.save(categoryToSave));
    }

    @Transactional(readOnly = true)
    public CategoryPageResponse pagination(Integer pageNumber) throws NotFoundException {
        Page<Category> page = getPage(pageNumber);
        validatePageNumber(page, pageNumber);

        String previousUrl = urlGetPrevious(pageNumber);
        String nextUrl = urlGetNext(page, pageNumber);

        return categoryMapper.buildPageResponse(page.getContent(), previousUrl, nextUrl);
    }

    @Transactional(readOnly = true)
    public List<String> getCategoryNames() throws EmptyListException {
        if (repository.findAllCategoryNames().isEmpty())
            throw new EmptyListException(messageSource.getMessage("error.categorylist.empty", null, Locale.US));
        return repository.findAllCategoryNames();
    }

    @Transactional
    public CategoryResponseDTO update(Long id, CategoryRequestDTO categoryRequestDTO) {
        Category category = repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Category " + messageSource.getMessage("not.found", null, Locale.US)));
        category = categoryMapper.updateCategory(category, categoryRequestDTO);
        return categoryMapper.entityToResponseDTO(repository.save(category));
    }

    public void delete(Long id) {
        if (repository.existsById(id)) throw new EntityNotFoundException(" Category with that id was not found.");
        repository.deleteById(id);
    }

    public void validatePageNumber (Page<?> page, Integer numberPage){
        if (page.getTotalPages() < numberPage) throw new EntityNotFoundException("Page does not have elements.");
    }

}