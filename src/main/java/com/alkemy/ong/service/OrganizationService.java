package com.alkemy.ong.service;

import com.alkemy.ong.dto.OrganizationRequestDTO;
import com.alkemy.ong.dto.response.OrganizationResponseDTO;
import com.alkemy.ong.exception.EmptyListException;

public interface OrganizationService {

    OrganizationResponseDTO getOrganizationPublic() throws EmptyListException;

    OrganizationResponseDTO update(OrganizationRequestDTO organizationRequestDTO) throws EmptyListException;

}

