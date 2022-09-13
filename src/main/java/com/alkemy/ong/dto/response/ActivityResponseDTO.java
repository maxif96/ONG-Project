package com.alkemy.ong.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityResponseDTO {

    public Long id;
    public String name;
    public String image;
    public String content;
    public LocalDateTime updateDate;

}
