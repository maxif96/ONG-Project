package com.alkemy.ong.service;

import com.alkemy.ong.dto.CommentRequestDTO;
import com.alkemy.ong.dto.response.CommentResponseDTO;
import javassist.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ICommentService {

    CommentResponseDTO save(CommentRequestDTO dto, HttpServletRequest request) throws Exception;

    void deleteById(Long id) throws NotFoundException;


    CommentResponseDTO updateComment(CommentRequestDTO commentRequestDTO, Long id, String token);


    List<CommentResponseDTO> findCommentByNewsId(Long newsId) throws Exception;

    CommentResponseDTO findById(Long id);

    List<CommentResponseDTO> getAllResponseDto();


}
