package com.alkemy.ong.service.mapper.comment;

import com.alkemy.ong.dto.CommentRequestDTO;
import com.alkemy.ong.dto.response.CommentResponseDTO;
import com.alkemy.ong.model.Comment;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.repository.UserRepository;
import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;

@Component
public class CommentMapper {

    @Autowired
    private UserRepository usersRepository;
    @Autowired
    private NewsRepository newsRepository;

    public CommentMapper() {
    }

    public CommentRequestDTO commentToDto(Comment comment) {
        CommentRequestDTO commentRequestDTO = new CommentRequestDTO();
        commentRequestDTO.setBody(comment.getBody());
        commentRequestDTO.setNewsId(comment.getNews().getId());
        return commentRequestDTO;
    }

    public CommentResponseDTO entityToResponseDTO (Comment comment){
        return CommentResponseDTO.builder()
                .id(comment.getId())
                .body(comment.getBody())
                .newsId(comment.getNews().getId())
                .userId(comment.getUser().getId())
                .build();
    }

    public Comment entityToDTO(CommentRequestDTO requestDTO, Long userId) {
        return Comment.builder()
                .body(requestDTO.getBody())
                .user(usersRepository.findById(userId)
                        .orElseThrow(() -> new UserNotFoundException("User with that id not found")))
                .news(newsRepository.findById(requestDTO.getNewsId())
                        .orElseThrow(() -> new EntityNotFoundException("New with that id not found.")))
                .build();
    }

    public CommentResponseDTO commentToResponseDto(Comment comment) {
        return CommentResponseDTO
                .builder()
                .id(comment.getId())
                .body(comment.getBody())
                .userId(comment.getUser().getId())
                .newsId(comment.getNews().getId())
                .build();
    }
}
