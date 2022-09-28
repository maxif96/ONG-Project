package com.alkemy.ong.service;

import com.alkemy.ong.dto.ActivityRequestDTO;
import com.alkemy.ong.exception.NameAlreadyExists;
import com.alkemy.ong.model.Activity;
import com.alkemy.ong.repository.ActivityRepository;
import com.alkemy.ong.service.impl.ActivityServiceImpl;
import com.alkemy.ong.context.InMemoryUserDetails;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
@SpringBootTest
@ExtendWith(SpringExtension.class)
public class ActivityServiceImplTest {

    @Autowired
    private ActivityServiceImpl activityService;

    @MockBean
    private ActivityRepository repository;

    @Test
    void should_return_NameAlreadyExistsException_if_try_to_create_an_existing_name_activity () {
        when(repository.existsByName(anyString())).thenReturn(true);
        ActivityRequestDTO activityRequestDTO = ActivityRequestDTO.builder()
                .name("anyName")
                .build();

        assertThrows(NameAlreadyExists.class, () -> activityService.create(activityRequestDTO));

    }



}
