package com.alkemy.ong.controller;

import com.alkemy.ong.dto.MemberRequestDTO;
import com.alkemy.ong.dto.MemberResponseDTO;
import com.alkemy.ong.exception.ApiError;
import com.alkemy.ong.service.MemberService;
import com.alkemy.ong.util.MemberPageResponse;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

import static com.alkemy.ong.util.Constants.*;


@RestController
@RequestMapping("/members")
public class MemberController {

    @Autowired
    private MemberService memberService;
    @Autowired
    private MessageSource messageSource;

    @GetMapping("/get-all")
    public ResponseEntity<MemberPageResponse> getMembers(@RequestParam(defaultValue = "1") Integer pageNumber) throws NotFoundException {
        return ResponseEntity.ok().body(memberService.getMembersPage(pageNumber));
}

    @GetMapping()
    public ResponseEntity<?> getAllMembers(){
        List<MemberResponseDTO> dtoList = memberService.getAll();
        if(dtoList == null  || dtoList.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(messageSource.getMessage( "error.memberList.empty", null, Locale.US));
        }
        return ResponseEntity.ok().body(dtoList);
    }

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody MemberRequestDTO memberRequestDTO){
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(memberService.create(memberRequestDTO));
        } catch (Exception e){
            throw new ApiError(HttpStatus.BAD_REQUEST, e);
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> updateMember (@Valid @PathVariable(value = "id") Long id, @RequestBody MemberRequestDTO memberRequestDTO ) {
        MemberResponseDTO memberResponseDTOResponse = memberService.updateMember(memberRequestDTO,id);
        if(memberResponseDTOResponse ==null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Member "+messageSource.getMessage
                            ("error.memberList.empty", null, Locale.US));
        }
        return ResponseEntity.status(HttpStatus.OK).body(memberResponseDTOResponse);
    }


    @DeleteMapping("{id}")
    public ResponseEntity<?> removeMember(@PathVariable(value = "id") Long id) {
        try {
            memberService.removeMember(id);
            return ResponseEntity
                    .ok()
                    .body(messageSource.getMessage("deleted.member", null, Locale.US));
        } catch (Exception exception) {
            throw new ApiError(HttpStatus.NOT_FOUND, exception);
        }
    }
}

