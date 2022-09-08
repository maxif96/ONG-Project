package com.alkemy.ong.dto;
import lombok.*;

import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrganizationRequestDTO {

    @NotNull(message = "Name can not be empty.")
    private String name;
    @NotNull(message = "Image can not be empty.")
    private String image;
    @NotNull(message = "Address can not be empty.")
    private String address;
    @Email
    @NotNull(message = "Email can not be empty.")
    private String email;
    @NotNull(message = "Phone can not be empty.")
    private Integer phone;
    private String welcomeText;
    private String aboutUsText;
    private String urlFacebook;
    private String urlLinkedin;
    private String urlInstagram;
    private List<Long> slidesId;

}
