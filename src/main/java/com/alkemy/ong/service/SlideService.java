package com.alkemy.ong.service;

import com.alkemy.ong.dto.SlideRequestDTO;
import com.alkemy.ong.dto.response.SlideResponseDTO;
import com.alkemy.ong.exception.EmptyListException;

import java.util.List;

public interface SlideService {

    SlideResponseDTO getById(Long id);

    List<SlideResponseDTO> getAllSlides() throws EmptyListException;

    List<SlideResponseDTO> getAllSlidesById(Long id) throws EmptyListException;

    SlideResponseDTO createSlides(SlideRequestDTO slidesRequest) throws Exception;

    void deleteById(Long id);

    SlideResponseDTO update(Long id, SlideRequestDTO requestDTO);

}
