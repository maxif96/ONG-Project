package com.alkemy.ong.dto.response;

import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ActivityResponseDTO {

    public Long id;
    public String name;
    public String image;
    public String content;
    public LocalDateTime updateDate;

}
