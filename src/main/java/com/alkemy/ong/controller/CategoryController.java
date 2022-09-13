package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CategoryRequestDTO;
import com.alkemy.ong.dto.response.CategoryPageResponse;
import com.alkemy.ong.dto.response.CategoryResponseDTO;
import com.alkemy.ong.exception.EmptyListException;
import com.alkemy.ong.service.CategoryService;

import java.util.List;
import javax.validation.Valid;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<CategoryResponseDTO> create(@Valid @RequestBody CategoryRequestDTO categoryRequestDTO) throws Exception {
        return ResponseEntity.status(CREATED).body(categoryService.create(categoryRequestDTO));
    }

    @GetMapping("/get-all")
    public ResponseEntity<CategoryPageResponse> getPage (@RequestParam(defaultValue = "1") Integer page) throws NotFoundException {
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
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        categoryService.delete(id);
        return ResponseEntity.ok().body("Category successfully deleted");
    }
}