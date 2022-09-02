package com.alkemy.ong.service;

import com.alkemy.ong.dto.MemberRequestDTO;
import com.alkemy.ong.dto.MemberResponseDTO;
import com.alkemy.ong.util.MemberPageResponse;
import javassist.NotFoundException;

import java.util.List;

public interface MemberService {

    MemberPageResponse getMembersPage(Integer pageNumber) throws NotFoundException;
    MemberResponseDTO create(MemberRequestDTO memberRequestDTO);
    List<MemberResponseDTO> getAll();
    MemberResponseDTO updateMember(MemberRequestDTO memberUpdate, Long id);
    void removeMember(Long id);
}

