package com.alkemy.ong.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;
@Getter @Setter
@Builder
public class NewsResponseDTO {

    private Long id;
    private String name;
    private String content;
    private String image;
    private Long categoryId;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;

}
