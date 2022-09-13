package com.alkemy.ong.controller;

import com.alkemy.ong.dto.MemberRequestDTO;
import com.alkemy.ong.dto.response.MemberResponseDTO;
import com.alkemy.ong.exception.EmptyListException;
import com.alkemy.ong.service.MemberService;
import com.alkemy.ong.dto.response.MemberPageResponse;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MessageSource messageSource;

    @PostMapping
    public ResponseEntity<MemberResponseDTO> create(@Valid @RequestBody MemberRequestDTO memberRequestDTO){
            return ResponseEntity.status(HttpStatus.CREATED).body(memberService.createMember(memberRequestDTO));
    }
    @GetMapping("/get-all")
    public ResponseEntity<MemberPageResponse> getMembersPage(@RequestParam(defaultValue = "1") Integer pageNumber) throws NotFoundException {
        return ResponseEntity.ok().body(memberService.getMembersPage(pageNumber));
}
    @GetMapping
    public ResponseEntity<List<MemberResponseDTO>> getAll() throws EmptyListException {
        List<MemberResponseDTO> allMembers = memberService.getAll();
        return ResponseEntity.ok().body(allMembers);
    }
    @PutMapping("/{id}")
    public ResponseEntity<MemberResponseDTO> update(@Valid @PathVariable(value = "id") Long id, @RequestBody MemberRequestDTO memberRequestDTO ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(memberService.updateMember(id, memberRequestDTO));
    }
    @DeleteMapping("{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Long id) {
        memberService.deleteMember(id);
        return ResponseEntity.ok().body("Member successfully deleted");
    }
}

