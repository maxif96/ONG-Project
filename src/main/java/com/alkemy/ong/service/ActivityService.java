package com.alkemy.ong.service;

import com.alkemy.ong.dto.ActivityRequestDTO;
import com.alkemy.ong.dto.response.ActivityPageResponse;
import com.alkemy.ong.dto.response.ActivityResponseDTO;
import com.alkemy.ong.exception.NameAlreadyExists;
import javassist.NotFoundException;

public interface ActivityService {

    ActivityResponseDTO update(Long id, ActivityRequestDTO activityRequestDTO);

    ActivityResponseDTO create(ActivityRequestDTO activityRequestDTO) throws NameAlreadyExists;

    void delete(Long id);

    ActivityPageResponse pagination(Integer pageNumber) throws NotFoundException;

}
