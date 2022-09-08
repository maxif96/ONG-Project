package com.alkemy.ong.service;
import com.alkemy.ong.dto.OrganizationRequestDTO;
import com.alkemy.ong.dto.response.OrganizationResponseDTO;
import com.alkemy.ong.dto.response.SlideResponseDTO;

import java.util.List;

public interface OrganizationService {

    OrganizationResponseDTO getOrganizationPublic();

    OrganizationResponseDTO update(OrganizationRequestDTO organizationRequestDTO);

}

