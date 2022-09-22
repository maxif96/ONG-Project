package com.alkemy.ong.service;

import com.alkemy.ong.dto.CommentRequestDTO;
import com.alkemy.ong.dto.response.CommentPageResponse;
import com.alkemy.ong.dto.response.CommentResponseDTO;
import com.alkemy.ong.exception.EmptyListException;
import com.alkemy.ong.exception.UnauthorizedException;
import javassist.NotFoundException;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface CommentService {

    CommentResponseDTO create(CommentRequestDTO dto, HttpServletRequest request) throws Exception;

    List<CommentResponseDTO> getAll() throws EmptyListException;

    CommentPageResponse pagination(Integer pageNumber) throws NotFoundException;

    void delete(Long id, HttpServletRequest request) throws NotFoundException, UnauthorizedException;

    CommentResponseDTO update(CommentRequestDTO commentRequestDTO, Long id, String token) throws UnauthorizedException;

}
