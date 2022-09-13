package com.alkemy.ong.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewsRequestDTO {

    @NotBlank(message = "Name can not be empty.")
    private String name;

    @NotBlank(message = "Content can not be empty.")
    private String content;

    private String image;

    private Long categoryId;

}