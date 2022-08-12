package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.CommentDto;
import com.alkemy.ong.dto.response.CommentResponseDTO;
import com.alkemy.ong.model.Comment;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.service.ICommentService;
import com.alkemy.ong.service.mapper.comment.CommentMapper;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private CommentMapper commentMapper;

    @Override
    @Transactional
    public CommentDto save(CommentDto dto) {
        try {
            Comment comment = commentMapper.dtoToComment(dto);
            Comment commentSaved = commentRepository.save(comment);
            return commentMapper.commentToDto(commentSaved);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long id) throws NotFoundException {
        try {
            Optional<Comment> comment = commentRepository.findById(id);
            if (comment.isPresent()){
                commentRepository.deleteById(id);
            }
        } catch (Exception e) {
            throw new NotFoundException("Comment not found");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDTO> getAllResponseDto() {
        return commentRepository.findAllByOrderByCreateAtAsc()
                .stream()
                .map(comment -> commentMapper.commentToResponseDto(comment))
                .collect(Collectors.toList());
    }
}
