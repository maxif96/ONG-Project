package com.alkemy.ong.dto;

import java.util.Date;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CategoryRequestDTO {

    @NotBlank(message = "Name may not be null")
    @Pattern(regexp = "^[a-zA-Z0-9]{1,20}$", message = "This field only accepts letters and numbers.")
    private String name;

    private String description;

    private String image;

}
