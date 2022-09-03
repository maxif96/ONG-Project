package com.alkemy.ong.util;

import com.alkemy.ong.dto.response.MemberResponseDTO;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class MemberPageResponse {

    private List<MemberResponseDTO> members;
    private String nextUrl;
    private String previousUrl;

}