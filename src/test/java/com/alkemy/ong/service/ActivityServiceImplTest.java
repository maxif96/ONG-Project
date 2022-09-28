package com.alkemy.ong.service;

import com.alkemy.ong.dto.ActivityRequestDTO;
import com.alkemy.ong.dto.response.ActivityResponseDTO;
import com.alkemy.ong.exception.NameAlreadyExists;
import com.alkemy.ong.model.Activity;
import com.alkemy.ong.repository.ActivityRepository;
import com.alkemy.ong.service.impl.ActivityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureTestDatabase
public class ActivityServiceImplTest {

    @Autowired
    private ActivityServiceImpl activityService;

    @MockBean
    private ActivityRepository mockRepository;

    @Test
    void should_return_NameAlreadyExistsException_if_try_to_create_an_activity_with_an_existing_activity_name() {
        when(mockRepository.existsByName(anyString())).thenReturn(true);
        ActivityRequestDTO activityRequestDTO = ActivityRequestDTO.builder()
                .name("anyName")
                .build();

        assertThrows(NameAlreadyExists.class, () -> activityService.create(activityRequestDTO));

    }

    @Test
    void should_return_an_ActivityResponseDTO_when_create_an_Activity() throws NameAlreadyExists {
        ActivityRequestDTO activityRequestDTO = ActivityRequestDTO.builder()
                .name("anyName")
                .content("contentTest")
                .image("imageTest")
                .build();

        when(mockRepository.save(any())).thenReturn(new Activity());

        assertEquals(ActivityResponseDTO.class, activityService.create(activityRequestDTO).getClass());
    }

    @Test
    void should_create_an_Activity_and_return_it_as_a_Response() throws NameAlreadyExists {

        Activity activity = Activity.builder()
                .id(1L)
                .name("testName")
                .content("contentTest")
                .image("imageTest")
                .build();

        ActivityResponseDTO responseDTO = ActivityResponseDTO.builder()
                .id(1L)
                .name("testName")
                .content("contentTest")
                .image("imageTest")
                .build();

        ActivityRequestDTO activityRequestDTO = ActivityRequestDTO.builder()
                .name("testName")
                .content("contentTest")
                .image("imageTest")
                .build();

        when(mockRepository.save(any())).thenReturn(activity);

        assertEquals(activityService.create(activityRequestDTO).toString(), responseDTO.toString());

    }




}
