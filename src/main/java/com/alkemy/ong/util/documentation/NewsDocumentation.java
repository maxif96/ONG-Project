package com.alkemy.ong.util.documentation;

import com.alkemy.ong.dto.NewsRequestDTO;
import com.alkemy.ong.dto.response.CommentResponseDTO;
import com.alkemy.ong.dto.response.NewsPageResponse;
import com.alkemy.ong.dto.response.NewsResponseDTO;
import com.alkemy.ong.exception.NameAlreadyExists;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javassist.NotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface NewsDocumentation {

    @ApiOperation(value = "Create a new.")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "New was created successfully"),
            @ApiResponse(code = 400, message = "Bad request. Some field is invalid." )})
    @ApiParam(value = "New to create body", required = true)
    ResponseEntity<NewsResponseDTO> create(NewsRequestDTO newsDto);

    @ApiOperation(value = "Get a page with 10 items according to the page number.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Page found successfully"),
            @ApiResponse(code = 404, message = "Page doesn't have elements" )})
    @ApiParam(value = "Number of page", required = true, defaultValue = "1")
    ResponseEntity<NewsPageResponse> getNewsPage(Integer page) throws NotFoundException;

    @ApiOperation(value = "Get a New by id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "New found successfully."),
            @ApiResponse(code = 404, message = "New with that id was not found")})
    @ApiParam(value = "New id", required = true)
    ResponseEntity<NewsResponseDTO> details(Long id) throws NameAlreadyExists;

    @ApiOperation(value = "Update a new by id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "New updated successfully"),
            @ApiResponse(code = 404, message = "New with that id doesn't exist")})
    @ApiParam(value = "New id", required = true)
    ResponseEntity<NewsResponseDTO> update(Long id, NewsRequestDTO newsUpdate);

    @ApiOperation(value = "Soft delete a new.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "New deleted successfully"),
            @ApiResponse(code = 404, message = "New with that id doesn't exist")})
    @ApiParam(value = "New id", required = true)
    ResponseEntity<String> delete(Long id);

    @ApiOperation(value = "Find comment by new id.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Comment found successfully"),
            @ApiResponse(code = 404, message = "New with that id doesn't exist"),
            @ApiResponse(code = 404, message = "New with that id doesn't have any comments")})
    @ApiParam(value = "New id", required = true)
    ResponseEntity<List<CommentResponseDTO>> findCommentsByNewsId(Long newsId) throws Exception;

}
