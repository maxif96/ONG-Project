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
public class ActivityPageResponse {

    private List<ActivityResponseDTO> activities;
    private String previousUrl;
    private String nextUrl;

}
