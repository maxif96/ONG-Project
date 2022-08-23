package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.CategoryRequestDTO;
import com.alkemy.ong.dto.response.CategoryPageResponse;
import com.alkemy.ong.dto.response.CategoryResponseDTO;
import com.alkemy.ong.exception.EmptyListException;
import com.alkemy.ong.exception.ResourceNotFoundException;
import com.alkemy.ong.model.Category;
import com.alkemy.ong.repository.CategoryRepository;
import com.alkemy.ong.service.CategoryService;
import com.alkemy.ong.service.mapper.CategoryMapper;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;

import com.alkemy.ong.util.PaginationUtil;
import com.amazonaws.services.kms.model.AlreadyExistsException;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Locale;

@Service
public class CategoryServiceImpl extends PaginationUtil<Category, Long, CategoryRepository> implements CategoryService {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private CategoryMapper categoryMapper;

    @Transactional
    public CategoryResponseDTO createCategory(CategoryRequestDTO categoryRequestDTO) throws Exception {
        Category category = repository
                .findByName(categoryRequestDTO.getName())
                .orElseThrow(() ->new AlreadyExistsException(
                        messageSource.getMessage("error.category.already.exists", null, Locale.US)));
        return categoryMapper.entityToResponseDTO(repository.save(category));
    }

    public CategoryPageResponse getAllCategories(Integer numberOfPage) throws NotFoundException {
        if (numberOfPage < 1) throw new NotFoundException(messageSource.getMessage("resource.not.found", null, Locale.US));

        Page<Category> page = getPage(numberOfPage);
        String previousUrl = urlGetPrevious(numberOfPage);
        String nextUrl = urlGetNext(page, numberOfPage);



    }

    public List<String> getCategoryNames() throws EmptyListException {
        if (categoryRepository.findAllCategoryNames().isEmpty())
            throw new EmptyListException(messageSource.getMessage("error.categorylist.empty", null, Locale.US));
        return categoryRepository.findAllCategoryNames();
    }

    @Transactional
    public CategoryResponseDTO update(Long id, CategoryRequestDTO categoryRequestDTO) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Category " + messageSource.getMessage("not.found", null, Locale.US)));
        category = categoryMapper.updateCategory(category, categoryRequestDTO);
        return categoryMapper.entityToResponseDTO(categoryRepository.save(category));
    }

//    @Override
//    public CategoryRequestDTO findById(Long id) {
//        Optional<Category> res = categoryRepository.findById(id);
//        if (res.isPresent()) {
//            Category category = res.get();
//            return categoryMapper.categoryToCategoryDTO(category);
//        } else {
//           throw new EntityNotFoundException(messageSource.getMessage("category.notFound", null, Locale.US));
//        }
//    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "id", id));
        categoryRepository.deleteById(id);
    }
}