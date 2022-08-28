package com.alkemy.ong.service.mapper.comment;

import com.alkemy.ong.dto.CommentRequestDTO;
import com.alkemy.ong.dto.response.CommentPageResponse;
import com.alkemy.ong.dto.response.CommentResponseDTO;
import com.alkemy.ong.model.Comment;
import com.alkemy.ong.repository.NewsRepository;
import com.alkemy.ong.repository.UserRepository;
import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CommentMapper {

    @Autowired
    private UserRepository usersRepository;
    @Autowired
    private NewsRepository newsRepository;

    public CommentMapper() {
    }

    public CommentResponseDTO entityToResponseDTO (Comment comment){
        return CommentResponseDTO.builder()
                .id(comment.getId())
                .body(comment.getBody())
                .newsId(comment.getNews().getId())
                .userId(comment.getUser().getId())
                .build();
    }

    public Comment requestDTOToEntity(CommentRequestDTO requestDTO, Long userId) {
        return Comment.builder()
                .body(requestDTO.getBody())
                .user(usersRepository.findById(userId)
                        .orElseThrow(() -> new UserNotFoundException("User with that id not found")))
                .news(newsRepository.findById(requestDTO.getNewsId())
                        .orElseThrow(() -> new EntityNotFoundException("New with that id not found.")))
                .build();
    }

    public CommentPageResponse buildPageResponse(List<Comment> content, String previousUrl, String nextUrl) {
        return CommentPageResponse.builder()
                .comments(content
                        .stream()
                        .map(this::entityToResponseDTO)
                        .collect(Collectors.toList()))
                .previousUrl(previousUrl)
                .nextUrl(nextUrl)
                .build();
    }
}
