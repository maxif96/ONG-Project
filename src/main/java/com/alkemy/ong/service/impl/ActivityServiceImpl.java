package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.ActivityRequestDTO;
import com.alkemy.ong.dto.response.ActivityPageResponse;
import com.alkemy.ong.dto.response.ActivityResponseDTO;
import com.alkemy.ong.exception.NameAlreadyExists;
import com.alkemy.ong.model.Activity;
import com.alkemy.ong.repository.ActivityRepository;
import com.alkemy.ong.service.ActivityService;
import com.alkemy.ong.service.mapper.ActivityMapper;
import com.alkemy.ong.util.PaginationUtil;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Locale;

@Service
public class ActivityServiceImpl extends PaginationUtil<Activity, Long, ActivityRepository> implements ActivityService {

    @Autowired
    private MessageSource messageSource;
    @Autowired
    private ActivityMapper activityMapper;

    @Transactional
    public ActivityResponseDTO create(ActivityRequestDTO activityRequestDTO) throws NameAlreadyExists {
        if (repository.existsByName(activityRequestDTO.getName()))
            throw new NameAlreadyExists(messageSource.getMessage("error.activity.already.exists", null, Locale.US));
        Activity activity = activityMapper.requestDTOToEntity(activityRequestDTO);
        Activity activitySaved = repository.save(activity);
        return activityMapper.entityToResponseDTO(activitySaved);
    }

    @Transactional(readOnly = true)
    public ActivityPageResponse pagination(Integer pageNumber) throws NotFoundException {

        Page<Activity> page = getPage(pageNumber);
        validatePageNumber(page, pageNumber);
        String previousUrl = urlGetPrevious(pageNumber);
        String nextUrl = urlGetNext(page, pageNumber);

        return activityMapper.buildPageResponse(page.getContent(), previousUrl, nextUrl);
    }

    @Transactional
    public ActivityResponseDTO update(Long id, ActivityRequestDTO activityRequestDTO) {
        Activity activity = repository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Activity " + messageSource.getMessage(
                                "not.found", null, Locale.US)));

        activity = activityMapper.updateEntity(activityRequestDTO, activity);
        Activity activityUpdated = repository.save(activity);

        return activityMapper.entityToResponseDTO(activityUpdated);
    }

    @Transactional
    public void delete(Long id) {
        if (repository.existsById(id)) repository.deleteById(id);
        else throw new EntityNotFoundException("Activity was not found.");
    }

    public void validatePageNumber (Page<?> page, Integer numberPage){
        if (page.getTotalPages() < numberPage) throw new EntityNotFoundException("Page does not have elements.");
    }

}
