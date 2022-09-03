package com.alkemy.ong.service.mapper;

import com.alkemy.ong.dto.MemberRequestDTO;
import com.alkemy.ong.dto.response.MemberResponseDTO;
import com.alkemy.ong.model.Member;
import com.alkemy.ong.util.MemberPageResponse;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MemberMapper {

    public MemberResponseDTO entityToResponseDTO(Member member){
        return MemberResponseDTO.builder()
                .id(member.getId())
                .name(member.getName())
                .facebookUrl(member.getFacebookUrl())
                .instagramUrl(member.getInstagramUrl())
                .linkedinUrl(member.getLinkedinUrl())
                .description(member.getDescription())
                .image(member.getImage())
                .updateDate(member.getUpdatedAt())
                .build();
    }

    public Member requestDTOTOEntity(MemberRequestDTO memberRequestDTO){
        return Member.builder()
                .name(memberRequestDTO.getName())
                .facebookUrl(memberRequestDTO.getFacebookUrl())
                .instagramUrl(memberRequestDTO.getInstagramUrl())
                .linkedinUrl(memberRequestDTO.getLinkedinUrl())
                .description(memberRequestDTO.getDescription())
                .createdAt(new Date())
                .image(memberRequestDTO.getImage())
                .build();
    }

    public MemberPageResponse buildPage(List<Member> content, String previousUrl, String nextUrl) {
        return MemberPageResponse.builder()
                .members(content.stream()
                        .map(this::entityToResponseDTO)
                        .collect(Collectors.toList()))
                .previousUrl(previousUrl)
                .nextUrl(nextUrl)
                .build();
    }

    public Member updateMember(Member memberFromDatabase, MemberRequestDTO memberToUpdateRequest) {
        return Member.builder()
                .id(memberFromDatabase.getId())
                .name(memberToUpdateRequest.getName())
                .description(memberToUpdateRequest.getDescription())
                .facebookUrl(memberToUpdateRequest.getFacebookUrl())
                .instagramUrl(memberToUpdateRequest.getInstagramUrl())
                .linkedinUrl(memberToUpdateRequest.getLinkedinUrl())
                .image(memberToUpdateRequest.getImage())
                .createdAt(memberFromDatabase.getCreatedAt())
                .updatedAt(new Date())
                .build();
    }
}
