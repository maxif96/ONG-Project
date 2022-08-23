package com.alkemy.ong.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryPageResponse {

    private List<CategoryResponseDTO> categories;
    private String previous;
    private String next;

}
