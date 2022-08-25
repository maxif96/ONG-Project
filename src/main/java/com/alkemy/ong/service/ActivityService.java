package com.alkemy.ong.service;

import com.alkemy.ong.dto.ActivityRequestDTO;
import com.alkemy.ong.dto.response.ActivityResponseDTO;
import com.alkemy.ong.exception.NameAlreadyExists;

import java.util.List;

public interface ActivityService {

    ActivityResponseDTO update (Long id, ActivityRequestDTO activityRequestDTO);

    ActivityResponseDTO save (ActivityRequestDTO activityRequestDTO) throws NameAlreadyExists;


}
