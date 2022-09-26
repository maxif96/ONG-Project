package com.alkemy.ong.dto.response;

import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TestimonialResponseDTO {

    private Long id;
    private String name;
    private String image;
    private String content;
    private LocalDateTime createdOn;
    private LocalDateTime updatedOn;

}
