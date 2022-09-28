package com.alkemy.ong.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ActivityRequestDTO {

    @NotNull(message = "{error.name.notnull}")
    @Pattern(regexp = "[a-zA-Z\\d\\s]*", message = "{error.regex}")
    public String name;
    @NotNull(message = "{error.content.notnull}")
    public String content;
    @NotNull(message = "{error.image.notnull}")
    public String image;

}
