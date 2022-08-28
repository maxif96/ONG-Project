package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CommentRequestDTO;
import com.alkemy.ong.dto.response.CommentResponseDTO;
import com.alkemy.ong.exception.UnauthorizedException;
import com.alkemy.ong.service.CommentService;
import com.alkemy.ong.service.UserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Locale;
import java.util.List;


@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private UserService userService;


    @PostMapping
    public ResponseEntity<CommentResponseDTO> save(
            @Valid @RequestBody CommentRequestDTO commentRequestDTO, HttpServletRequest request) throws Exception {
        CommentResponseDTO commentSaved = commentService.create(commentRequestDTO, request);
        return ResponseEntity.ok().body(commentSaved);
    }


    @PutMapping("/{id}")
    public ResponseEntity<CommentResponseDTO> update(
            @Valid @RequestBody CommentRequestDTO commentRequestDTO,
            @PathVariable("id") Long id, @RequestHeader(name = "Authorization") String token) throws UnauthorizedException {
        return ResponseEntity.ok().body(commentService.update(commentRequestDTO, id, token));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(value = "id") Long id, HttpServletRequest request) throws UnauthorizedException, NotFoundException {
        commentService.delete(id, request);
        return ResponseEntity.ok().body("Comment successfully deleted.");
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<CommentResponseDTO> response = commentService.getAll();
        if(response == null || response.isEmpty()){
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(messageSource.getMessage("error.commentList.empty", null, Locale.US));
        }
        return ResponseEntity.ok(commentService.getAll());
    }


}
