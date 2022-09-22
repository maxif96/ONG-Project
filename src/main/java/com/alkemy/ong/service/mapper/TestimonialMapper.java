package com.alkemy.ong.service.mapper;

import com.alkemy.ong.dto.TestimonialRequestDTO;
import com.alkemy.ong.dto.response.TestimonialPageResponse;
import com.alkemy.ong.dto.response.TestimonialResponseDTO;
import com.alkemy.ong.model.Testimonial;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TestimonialMapper {

    public TestimonialResponseDTO entityToResponseDTO(Testimonial testimonial) {
        return TestimonialResponseDTO
                .builder()
                .id(testimonial.getId())
                .name(testimonial.getName())
                .image(testimonial.getImage())
                .content(testimonial.getContent())
                .createdOn(testimonial.getCreatedOn())
                .updatedOn(testimonial.getUpdatedOn())
                .build();
    }

    public Testimonial requestDTOToEntity(TestimonialRequestDTO testimonialRequestDTO) {
        return Testimonial
                .builder()
                .name(testimonialRequestDTO.getName())
                .image(testimonialRequestDTO.getImage())
                .content(testimonialRequestDTO.getContent())
                .build();
    }

    public Testimonial updateTestimonial(TestimonialRequestDTO testimonialRequestDTO, Testimonial testimonial) {
        return Testimonial.builder()
                .id(testimonial.getId())
                .name(testimonialRequestDTO.getName())
                .image(testimonialRequestDTO.getImage())
                .content(testimonialRequestDTO.getContent())
                .createdOn(testimonial.getCreatedOn())
                .updatedOn(testimonial.getUpdatedOn())
                .build();
    }

    public TestimonialPageResponse buildPage(List<Testimonial> testimonials, String previousUrl, String nextUrl) {

        return TestimonialPageResponse.builder()
                .testimonials(testimonials.stream()
                        .map(this::entityToResponseDTO)
                        .collect(Collectors.toList()))
                .previousUrl(previousUrl)
                .nextUrl(nextUrl)
                .build();

    }
}
