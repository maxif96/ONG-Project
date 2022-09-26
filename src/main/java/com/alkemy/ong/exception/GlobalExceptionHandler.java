package com.alkemy.ong.exception;

import java.io.IOException;
import java.time.LocalDateTime;

import com.amazonaws.services.kms.model.AlreadyExistsException;
import com.amazonaws.services.pinpoint.model.BadRequestException;
import javassist.NotFoundException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) ->{

            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });
        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({EntityNotFoundException.class,
            NotFoundException.class,
            IOException.class,
            EmptyListException.class
    })
    @ResponseBody
    public MessageResponse handleNotFound (Exception e, HttpServletRequest request){
        return new MessageResponse (LocalDateTime.now(), e, request);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler({NameAlreadyExists.class,
            BadCredentialsException.class,
            EmailAlreadyExists.class,
            AlreadyExistsException.class})
    @ResponseBody
    public MessageResponse handleAlreadyExists (Exception e, HttpServletRequest request) {
        return new MessageResponse(LocalDateTime.now(), e, request);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public MessageResponse handleBadRequest (Exception e, HttpServletRequest request){
        return new MessageResponse(LocalDateTime.now(), e, request);
    }

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ResponseBody
    @ExceptionHandler(UnauthorizedException.class)
    public MessageResponse handleUnauthorized (Exception e, HttpServletRequest request){
        return new MessageResponse(LocalDateTime.now(), e, request);
    }



}

    


