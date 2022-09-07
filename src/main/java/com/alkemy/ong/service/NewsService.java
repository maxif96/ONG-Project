package com.alkemy.ong.service;

import com.alkemy.ong.dto.NewsRequestDTO;
import com.alkemy.ong.dto.response.CommentResponseDTO;
import com.alkemy.ong.dto.response.NewsPageResponse;
import com.alkemy.ong.dto.response.NewsResponseDTO;
import com.alkemy.ong.exception.NameAlreadyExists;
import javassist.NotFoundException;

import java.util.List;

public interface NewsService {

    NewsResponseDTO createNews(NewsRequestDTO newsDto);
    NewsResponseDTO findNewsById(Long id) throws NameAlreadyExists;
    NewsResponseDTO updateNews(NewsRequestDTO newsRequestDTO, Long id);
    void deleteById(Long id);
    NewsPageResponse pagination (Integer numberOfPage) throws NotFoundException;
    List<CommentResponseDTO> findCommentByNewsId(Long newsId) throws Exception;
}
