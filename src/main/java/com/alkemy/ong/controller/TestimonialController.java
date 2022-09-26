package com.alkemy.ong.controller;

import com.alkemy.ong.dto.TestimonialRequestDTO;
import com.alkemy.ong.dto.response.TestimonialPageResponse;
import com.alkemy.ong.dto.response.TestimonialResponseDTO;
import com.alkemy.ong.service.TestimonialService;
import java.util.Locale;
import javax.validation.Valid;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/testimonials")
public class TestimonialController {
    
    @Autowired
    private TestimonialService testimonialService;
    
    @Autowired
    private MessageSource messageSource;

    @GetMapping("/page")
    public ResponseEntity<TestimonialPageResponse> getAllPagination(@RequestParam Integer pageNumber) throws NotFoundException {
        return ResponseEntity.ok().body(testimonialService.pagination(pageNumber));
    }
    
    @PostMapping
    public ResponseEntity<TestimonialResponseDTO> createTestimonial(@Valid @RequestBody TestimonialRequestDTO testimonialRequestDTO){
        return ResponseEntity.status(HttpStatus.CREATED).body(testimonialService.createTestimonial(testimonialRequestDTO));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TestimonialResponseDTO> editTestimonial(@Valid @RequestBody TestimonialRequestDTO testimonialRequestDTO,
                                                                  @PathVariable(value = "id") Long id) {
        return ResponseEntity.ok().body(testimonialService.updateTestimonial(testimonialRequestDTO, id));
    }
    
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteTestimonial(@PathVariable Long id) {
        try {
            testimonialService.deleteTestimonial(id);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
        
        return new ResponseEntity<>(messageSource.getMessage("deleted.testimonial", null, Locale.US), HttpStatus.OK);
    }
}