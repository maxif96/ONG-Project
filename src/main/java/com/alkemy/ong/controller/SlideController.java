package com.alkemy.ong.controller;

import com.alkemy.ong.dto.SlideRequestDTO;
import com.alkemy.ong.dto.response.SlideResponseDTO;
import com.alkemy.ong.exception.EmptyListException;
import com.alkemy.ong.service.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/slides")
public class SlideController {

    @Autowired
    private SlideService slideService;
    @Autowired
    private MessageSource messageSource;


    @PostMapping
    public ResponseEntity<SlideResponseDTO> create(@RequestBody SlideRequestDTO slideRequestDTO) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(slideService.createSlides(slideRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<SlideResponseDTO>> getAllSlides() throws EmptyListException {
        return ResponseEntity.ok().body(slideService.getAllSlides());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SlideResponseDTO> getById(@PathVariable Long id) {
        SlideResponseDTO slideResponse = slideService.getById(id);
        return ResponseEntity.ok().body(slideResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SlideResponseDTO> update(@PathVariable Long id,
                                                   @RequestBody @Valid SlideRequestDTO request) {
        SlideResponseDTO response = slideService.update(id, request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
        slideService.deleteById(id);
        return ResponseEntity.ok().body("Slide successfully deleted.");
    }
}
