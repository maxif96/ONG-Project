package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.MemberRequestDTO;
import com.alkemy.ong.dto.response.MemberResponseDTO;
import com.alkemy.ong.exception.EmptyListException;
import com.alkemy.ong.model.Member;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.service.MemberService;
import com.alkemy.ong.service.mapper.MemberMapper;
import com.alkemy.ong.dto.response.MemberPageResponse;
import com.alkemy.ong.util.PaginationUtil;
import com.amazonaws.services.pinpoint.model.BadRequestException;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl extends PaginationUtil<Member, Long, MemberRepository> implements MemberService {
    @Autowired
    private MemberMapper memberMapper;

    public MemberResponseDTO createMember(MemberRequestDTO memberRequestDTO) {
        Member member = memberMapper.requestDTOTOEntity(memberRequestDTO);
        try {
            Member memberSaved = repository.save(member);
            return memberMapper.entityToResponseDTO(memberSaved);
        } catch (BadRequestException badRequestException) {
            throw new BadRequestException("Could not save the member.");
        }
    }

    @Transactional(readOnly = true)
    public List<MemberResponseDTO> getAll() throws EmptyListException {
        if (repository.count() < 1) throw new EmptyListException("No one member was found.");
        return repository.findAll().stream()
                .map(m -> memberMapper.entityToResponseDTO(m))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MemberPageResponse getMembersPage(Integer pageNumber) throws NotFoundException {
        Page<Member> page = getPage(pageNumber);
        String previousUrl = urlGetPrevious(pageNumber);
        String nextUrl = urlGetNext(page, pageNumber);

        if (pageNumber > page.getTotalPages()) throw new NotFoundException("Page doesn't have elements.");
        return memberMapper.buildPage(page.getContent(), previousUrl, nextUrl);
    }

    public MemberResponseDTO updateMember(Long id, MemberRequestDTO memberToUpdateRequest) {
        Member memberFromDatabase = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Member with that id was not found."));
        Member memberUpdate = memberMapper.updateMember(memberFromDatabase, memberToUpdateRequest);

        return memberMapper.entityToResponseDTO(repository.save(memberUpdate));
    }

    public void deleteMember(Long id) {
        if (!repository.existsById(id)) throw new EntityNotFoundException("Member with that id not found.");
        repository.deleteById(id);
    }
}

