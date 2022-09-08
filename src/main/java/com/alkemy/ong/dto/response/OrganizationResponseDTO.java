package com.alkemy.ong.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizationResponseDTO {

    private Long id;
    private String name;
    private String image;
    private String address;
    private String email;
    private Integer phone;
    private String aboutUsText;
    private String welcomeText;
    private String urlFacebook;
    private String urlLinkedin;
    private String urlInstagram;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<SlideResponseDTO> slides;
}
