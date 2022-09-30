package com.alkemy.ong.service;

import com.alkemy.ong.dto.ActivityRequestDTO;
import com.alkemy.ong.dto.response.ActivityPageResponse;
import com.alkemy.ong.dto.response.ActivityResponseDTO;
import com.alkemy.ong.exception.NameAlreadyExists;
import com.alkemy.ong.model.Activity;
import com.alkemy.ong.repository.ActivityRepository;
import com.alkemy.ong.service.impl.ActivityServiceImpl;
import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;

import javax.persistence.EntityNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureTestDatabase
public class ActivityServiceImplTest {

    @Autowired
    private ActivityServiceImpl activityService;

    @MockBean
    private ActivityRepository mockRepository;

    private ActivityRequestDTO activityRequestDTO;

    @BeforeEach
    private void setUp () {
        activityRequestDTO = ActivityRequestDTO.builder()
                .name("testName")
                .content("testContent")
                .image("testImage")
                .build();
    }

    
    // ------ CREATE METHOD ------
    
    @Test
    void should_return_NameAlreadyExistsException_if_try_to_create_an_activity_with_an_existing_activity_name() {
        when(mockRepository.existsByName(anyString())).thenReturn(true);

        assertThrows(NameAlreadyExists.class, () -> activityService.create(activityRequestDTO));

    }

    @Test
    void should_return_an_ActivityResponseDTO_when_create_an_Activity() throws NameAlreadyExists {
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


        when(mockRepository.save(any())).thenReturn(activity);

        assertEquals(activityService.create(activityRequestDTO).toString(), responseDTO.toString());

    }

    // ------ PAGINATION METHOD ------

    @Test
    void should_throw_an_EntityNotFoundException_when_the_argument_is_less_than_1 () throws NotFoundException {
        assertThrows(EntityNotFoundException.class, () -> activityService.pagination(-1));
    }

    @Test
    void should_return_an_Activity_page_with_one_element () throws NotFoundException {
        ActivityResponseDTO activityResponseDTO = ActivityResponseDTO.builder()
                .id(1L)
                .name("name")
                .content("content")
                .image("image")
                .build();
        ActivityPageResponse pageResponse = new ActivityPageResponse(
                List.of(activityResponseDTO), null, null
        );

        Page<Activity> activity = new PageImpl<>(List.of(Activity.builder()
                .id(1L)
                .name("name")
                .content("content")
                .image("image")
                .build()));

        when(mockRepository.findAll(PageRequest.of(0, 10))).thenReturn(activity);

        assertEquals(activityService.pagination(1).toString(), pageResponse.toString());
    }

    @Test
    void should_throw_an_EntityNotFoundException_when_the_argument_is_greater_than_the_total_number_page () {
        Page<Activity> activity = new PageImpl<>(List.of(Activity.builder()
                .id(1L)
                .name("name")
                .content("content")
                .image("image")
                .build()));

        int numberGreaterThanTheTotalOfPage = 100;

        when(mockRepository.findAll(PageRequest.of(numberGreaterThanTheTotalOfPage - 1, 10))).thenReturn(activity);

        assertThrows(EntityNotFoundException.class, () -> activityService.pagination(numberGreaterThanTheTotalOfPage));
    }

    // ------ UPDATE METHOD ------

    @Test
    void should_throw_an_EntityNotFoundException_if_an_activity_with_that_id_does_not_exist () {

        ActivityRequestDTO requestDTO = ActivityRequestDTO.builder().build();

        when(mockRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> activityService.update(1L, requestDTO));
    }

    @Test
    void should_return_an_updated_ActivityResponseDTO_after_the_update () {
        Activity activityFromDatabase = Activity.builder()
                .id(1L)
                .name("name")
                .image("image")
                .content("content")
                .build();

        when(mockRepository.findById(1L)).thenReturn(Optional.ofNullable(activityFromDatabase));


        ActivityResponseDTO activityResponseDTO = ActivityResponseDTO.builder()
                .id(1L)
                .name("testName")
                .image("image")
                .content("content")
                .build();


        when(mockRepository.save(any())).thenReturn(Activity.builder()
                .id(1L)
                .name(activityResponseDTO.getName())
                .image(activityResponseDTO.getImage())
                .content(activityResponseDTO.getContent())
                .build());

        assertEquals(activityResponseDTO.getName(), activityService.update(1L, activityRequestDTO).getName());


    }

    // ------ DELETE METHOD ------

    @Test
    void should_throw_an_EntityNotFoundException_if_does_not_found_an_activity () {
        when(mockRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> activityService.delete(1L));

    }

    @Test
    void should_successfully_delete_an_Activity () {
        when(mockRepository.existsById(1L)).thenReturn(true);

        activityService.delete(1L);

        verify(mockRepository, times(1)).deleteById(1L);

    }






}
