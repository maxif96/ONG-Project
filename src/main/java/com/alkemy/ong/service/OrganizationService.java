package com.alkemy.ong.service;

import com.alkemy.ong.dto.OrganizationRequestDTO;
import com.alkemy.ong.dto.response.OrganizationResponseDTO;

public interface OrganizationService {

    OrganizationResponseDTO getOrganizationPublic();

    OrganizationResponseDTO update(OrganizationRequestDTO organizationRequestDTO);

}

