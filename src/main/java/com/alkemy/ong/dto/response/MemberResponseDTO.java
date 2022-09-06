package com.alkemy.ong.dto.response;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberResponseDTO {

    private Long id;
    private String name;
    private String facebookUrl;
    private String instagramUrl;
    private String linkedinUrl;
    private String image;
    private String description;
    private LocalDateTime updateDate;

}
