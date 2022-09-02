package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.MemberRequestDTO;
import com.alkemy.ong.dto.MemberResponseDTO;
import com.alkemy.ong.exception.ResourceNotFoundException;
import com.alkemy.ong.model.Member;
import com.alkemy.ong.repository.MemberRepository;
import com.alkemy.ong.service.MemberService;
import com.alkemy.ong.service.mapper.MemberMapper;
import com.alkemy.ong.util.MemberPageResponse;
import com.alkemy.ong.util.PaginationUtil;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MemberServiceImpl extends PaginationUtil<Member, Long, MemberRepository> implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public MemberPageResponse getMembersPage(Integer pageNumber) throws NotFoundException {
        Page<Member> page = getPage(pageNumber);
        String previousUrl = urlGetPrevious(pageNumber);
        String nextUrl = urlGetNext(page, pageNumber);

        if (pageNumber > page.getTotalPages()) throw new NotFoundException("Page doesn't have elements.");
        return memberMapper.buildPage(page.getContent(), previousUrl, nextUrl);
    }

    @Override
    public MemberResponseDTO create(MemberRequestDTO memberRequestDTO) {
        Member member = memberMapper.requestDTOTOEntity(memberRequestDTO);
        Member newMember = repository.save(member);
        return memberMapper.entityToResponseDTO(newMember);
    }

    @Override
    public List<MemberResponseDTO> getAll() {
        return repository.findAll().stream()
                .map(m -> memberMapper.entityToResponseDTO(m))
                .collect(Collectors.toList());
    }


    private MemberResponseDTO findMemberById(Long id) {
        Optional<Member> member = repository.findById(id);
        if(member.isEmpty())return null;
        return memberMapper.entityToResponseDTO(member.get());
    }

    @Override
    public MemberResponseDTO updateMember(MemberRequestDTO memberUpdate, Long id) {
        MemberResponseDTO memberResponseDTO = findMemberById(id);
        if(memberResponseDTO ==null) return null;
//        memberUpdate.setId(id);
        Member member = memberMapper.requestDTOTOEntity(memberUpdate);
        return memberMapper.entityToResponseDTO(repository.save(member));
    }


    @Override
    public void removeMember(Long id) {
        Member member = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", "id", id));
        repository.delete(member);
    }
}

