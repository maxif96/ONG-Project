package com.alkemy.ong.service;

import com.alkemy.ong.dto.MemberRequestDTO;
import com.alkemy.ong.dto.response.MemberResponseDTO;
import com.alkemy.ong.exception.EmptyListException;
import com.alkemy.ong.dto.response.MemberPageResponse;
import javassist.NotFoundException;

import java.util.List;

public interface MemberService {

    MemberPageResponse getMembersPage(Integer pageNumber) throws NotFoundException;

    MemberResponseDTO createMember(MemberRequestDTO memberRequestDTO);

    List<MemberResponseDTO> getAll() throws EmptyListException;

    MemberResponseDTO updateMember(Long id, MemberRequestDTO memberUpdate);

    void deleteMember(Long id);
}

