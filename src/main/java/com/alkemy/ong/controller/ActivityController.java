package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ActivityRequestDTO;
import com.alkemy.ong.dto.response.ActivityPageResponse;
import com.alkemy.ong.dto.response.ActivityResponseDTO;
import com.alkemy.ong.exception.NameAlreadyExists;
import com.alkemy.ong.service.ActivityService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @PostMapping
    public ResponseEntity<ActivityResponseDTO> create(@Valid @RequestBody ActivityRequestDTO activityRequestDTO) throws NameAlreadyExists {
        ActivityResponseDTO activityCreated = activityService.create(activityRequestDTO);
        return ResponseEntity.status(CREATED).body(activityCreated);
    }

    @GetMapping("/get-all")
    public ResponseEntity<ActivityPageResponse> getAllWithPage (@RequestParam(defaultValue = "1")
                                                                    Integer pageNumber) throws NotFoundException {
        ActivityPageResponse pageResponse =activityService.getActivitiesPage(pageNumber);
        return ResponseEntity.ok().body(pageResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ActivityResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ActivityRequestDTO request) {
        ActivityResponseDTO response = activityService.update(id, request);
        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete (@PathVariable Long id){
        activityService.delete(id);
        return ResponseEntity.ok().body("Activity successfully deleted");
    }

}
