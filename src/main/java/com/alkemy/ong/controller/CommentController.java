package com.alkemy.ong.controller;

import com.alkemy.ong.dto.CommentRequestDTO;
import com.alkemy.ong.dto.response.CommentPageResponse;
import com.alkemy.ong.dto.response.CommentResponseDTO;
import com.alkemy.ong.exception.EmptyListException;
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

import static org.springframework.http.HttpStatus.CREATED;


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
    public ResponseEntity<CommentResponseDTO> create(
            @Valid @RequestBody CommentRequestDTO commentRequestDTO, HttpServletRequest request) throws Exception {
        CommentResponseDTO commentSaved = commentService.create(commentRequestDTO, request);
        return ResponseEntity.status(CREATED).body(commentSaved);
    }

    @GetMapping
    public ResponseEntity<List<CommentResponseDTO>> getAll() throws EmptyListException {
        return ResponseEntity.ok().body(commentService.getAll());
    }

    @GetMapping("/get-all")
    public ResponseEntity<CommentPageResponse> getCommentsPage (@RequestParam(defaultValue = "1") Integer pageNumber) throws NotFoundException {
        return ResponseEntity.ok().body(commentService.getCommentsPage(pageNumber));
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

}
