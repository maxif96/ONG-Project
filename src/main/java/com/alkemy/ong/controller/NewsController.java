package com.alkemy.ong.controller;

import com.alkemy.ong.dto.NewsRequestDTO;
import com.alkemy.ong.dto.response.CommentResponseDTO;
import com.alkemy.ong.dto.response.NewsPageResponse;
import com.alkemy.ong.dto.response.NewsResponseDTO;
import com.alkemy.ong.exception.NameAlreadyExists;
import com.alkemy.ong.service.NewsService;
import com.alkemy.ong.service.impl.CommentServiceImpl;
import com.alkemy.ong.util.documentation.NewsDocumentation;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Locale;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/news")
public class NewsController implements NewsDocumentation {

    @Autowired
    private NewsService newsService;

    @Autowired
    private MessageSource messageSource;
    
    @Autowired
    private CommentServiceImpl commentServiceImpl;


    @PostMapping
    public ResponseEntity<NewsResponseDTO> create(@Valid @RequestBody NewsRequestDTO newsRequestDTO){
        NewsResponseDTO newsCreated = newsService.createNews(newsRequestDTO);
        return ResponseEntity.status(CREATED).body(newsCreated);
    }

    @GetMapping("/get-all")
    public ResponseEntity<NewsPageResponse> getNewsPage (@RequestParam(defaultValue = "1") Integer page) throws NotFoundException {
        return ResponseEntity.ok().body(newsService.pagination(page));
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<NewsResponseDTO> details(@Valid @PathVariable(value = "id") Long id) throws NameAlreadyExists {
            return ResponseEntity.ok().body(newsService.findNewsById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<NewsResponseDTO> update(@Valid @PathVariable(value = "id") Long id, @RequestBody NewsRequestDTO newsRequestDTO) {
        return ResponseEntity.ok().body(newsService.updateNews(newsRequestDTO, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable(name = "id") Long id) {
            newsService.deleteById(id);
            return ResponseEntity.ok().body("News successfully deleted.");
    }

    @GetMapping("/{newsId}/comments")
    public ResponseEntity<List<CommentResponseDTO>> findCommentsByNewsId(@PathVariable("newsId") Long newsId) throws Exception {
        return ResponseEntity.ok().body(newsService.findCommentByNewsId(newsId));
    }

}
