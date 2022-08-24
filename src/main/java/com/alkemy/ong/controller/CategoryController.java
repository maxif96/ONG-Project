package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CategoryRequestDTO;
import com.alkemy.ong.dto.response.CategoryPageResponse;
import com.alkemy.ong.dto.response.CategoryResponseDTO;
import com.alkemy.ong.exception.EmptyListException;
import com.alkemy.ong.service.CategoryService;

import java.util.List;
import javax.validation.Valid;

import com.alkemy.ong.util.CategoryResponse;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import static com.alkemy.ong.util.Constants.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping()
    public ResponseEntity<?> createCategory(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO) throws Exception {
        return ResponseEntity.ok().body(categoryService.createCategory(categoryRequestDTO));
    }

//    @GetMapping(path = "/{id}")
//    public ResponseEntity<CategoryRequestDTO> categoryDetail(@PathVariable("id") Long id) {
//        CategoryRequestDTO responseCategoryRequestDTO = categoryService.findById(id);
//        return ResponseEntity.ok().body(responseCategoryRequestDTO);
//    }

    @GetMapping("/get-all")
    public ResponseEntity<CategoryPageResponse> categoriesPage (@RequestParam(defaultValue = "1") Integer page) throws NotFoundException {
        return ResponseEntity.ok().body(categoryService.getAllCategories(page));
    }

    @GetMapping
    public ResponseEntity<List<String>> getAllNames() throws EmptyListException {
        List<String> categoryNames = categoryService.getCategoryNames();
        return ResponseEntity.ok().body(categoryNames);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryResponseDTO> update(@PathVariable Long id, @Valid @RequestBody CategoryRequestDTO categoryRequestDTO) {
        CategoryResponseDTO response = categoryService.update(id, categoryRequestDTO);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable("id") Long id){
        categoryService.deleteCategory(id);
    }
}