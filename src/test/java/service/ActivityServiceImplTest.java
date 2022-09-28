package service;

import com.alkemy.ong.dto.ActivityRequestDTO;
import com.alkemy.ong.exception.NameAlreadyExists;
import com.alkemy.ong.model.Activity;
import com.alkemy.ong.repository.ActivityRepository;
import com.alkemy.ong.service.impl.ActivityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class ActivityServiceImplTest {

    @Autowired
    private ActivityServiceImpl activityService;

    @Autowired
    private ActivityRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

    }

    @Test
    void should_return_NameAlreadyExistsException_if_try_to_create_an_existing_activity  () {
        repository.save(Activity.builder()
                .name("nameDuplicated")
                .image("imageTest")
                .content("contentTest")
                .build());

        ActivityRequestDTO activityRequestDTO = ActivityRequestDTO.builder()
                .name("nameDuplicated")
                .image("imageTest")
                .content("contentTest")
                .build();

        assertThrows(NullPointerException.class, () -> {activityService.create(activityRequestDTO);});

    }

}
