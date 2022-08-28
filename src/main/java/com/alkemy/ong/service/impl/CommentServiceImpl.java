package com.alkemy.ong.service.impl;

import com.alkemy.ong.auth.utils.JwUtils;
import com.alkemy.ong.dto.CommentRequestDTO;

import com.alkemy.ong.exception.ResourceNotFoundException;
import com.alkemy.ong.exception.UnauthorizedException;
import com.alkemy.ong.model.Comment;
import com.alkemy.ong.model.Users;
import com.alkemy.ong.repository.CommentRepository;
import com.alkemy.ong.repository.UserRepository;

import com.alkemy.ong.dto.response.CommentResponseDTO;
import com.alkemy.ong.repository.NewsRepository;

import com.alkemy.ong.service.CommentService;
import com.alkemy.ong.service.UserService;
import com.alkemy.ong.service.mapper.comment.CommentMapper;
import java.util.ArrayList;

import com.alkemy.ong.util.PaginationUtil;
import com.amazonaws.services.cognitoidp.model.UserNotFoundException;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.Locale;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl extends PaginationUtil<Comment, Long, CommentRepository> implements CommentService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private NewsRepository newsRepository;
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private JwUtils jwUtil;

    @Transactional
    public CommentResponseDTO save(CommentRequestDTO requestDTO, HttpServletRequest request){
            final String authorizationHeader = request.getHeader("Authorization");

            String email = jwUtil.extractUsername(authorizationHeader.substring(7));
            Users loggedUser = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UserNotFoundException("You may be logged to leave a comment."));

            Comment commentToSave = commentMapper.requestDTOToEntity(requestDTO, loggedUser.getId());

            return commentMapper.entityToResponseDTO(repository.save(commentToSave));

    }

    @Transactional
    public void deleteById(Long id) throws NotFoundException {
        if(repository.existsById(id)) repository.deleteById(id);
        else throw new NotFoundException(messageSource.getMessage("error.comment.notFound", null, Locale.US));
    }


    public CommentResponseDTO update(CommentRequestDTO commentRequestDTO, Long id, String token) throws UnauthorizedException {
        String emailFromRequest = jwUtil.extractUsername(token.substring(7));
        Comment comment = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comment","id",id));

        if(!comment.getUser().getEmail().equalsIgnoreCase(emailFromRequest)) throw new UnauthorizedException("You are not authorized to update this comment.");

        comment.setBody(commentRequestDTO.getBody());
        return commentMapper.entityToResponseDTO(repository.save(comment));
    }

    public List<CommentResponseDTO> findCommentByNewsId(Long newsId) throws Exception {
        List<CommentResponseDTO> responseList = new ArrayList<>();
        for (Comment comment : repository.findAllByNewsId(newsId)){
            responseList.add(commentMapper.entityToResponseDTO(comment));
        }
        return responseList;
    }

    @Override
    public CommentResponseDTO findById(Long id) {
        Optional<Comment> res = repository.findById(id);
        if (res.isPresent()) {
            Comment comment = res.get();
            return commentMapper.entityToResponseDTO(comment);
        } else {
            throw new EntityNotFoundException(messageSource.getMessage("error.comment.notFound", null, Locale.US));
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<CommentResponseDTO> getAllResponseDto() {
        return repository.findAllByOrderByCreateAtAsc()
                .stream()
                .map(comment -> commentMapper.entityToResponseDTO(comment))
                .collect(Collectors.toList());


    }
}
