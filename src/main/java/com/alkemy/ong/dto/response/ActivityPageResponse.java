package com.alkemy.ong.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class ActivityPageResponse {

    private List<ActivityResponseDTO> activities;
    private String previousUrl;
    private String nextUrl;

}
