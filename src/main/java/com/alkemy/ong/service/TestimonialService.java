package com.alkemy.ong.service;

import com.alkemy.ong.dto.TestimonialRequestDTO;
import com.alkemy.ong.dto.response.TestimonialPageResponse;
import com.alkemy.ong.dto.response.TestimonialResponseDTO;
import javassist.NotFoundException;

public interface TestimonialService {

    TestimonialResponseDTO createTestimonial(TestimonialRequestDTO testimonialDto);

    TestimonialResponseDTO updateTestimonial(TestimonialRequestDTO testimonialDto, Long id);

    TestimonialPageResponse pagination(Integer numberPage) throws NotFoundException;

    void deleteTestimonial(Long id);

}