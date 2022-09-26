package com.alkemy.ong.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TestimonialPageResponse {

    private List<TestimonialResponseDTO> testimonials;
    private String previousUrl;
    private String nextUrl;
}
