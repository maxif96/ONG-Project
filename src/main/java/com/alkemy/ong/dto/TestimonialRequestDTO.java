package com.alkemy.ong.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Builder
@Data
public class TestimonialRequestDTO {

        @NotBlank(message = "Name is required.")
        private String name;

        private String image;

        @NotBlank(message = "Content is required.")
        private String content;

}
