package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.OrganizationRequestDTO;
import com.alkemy.ong.dto.response.OrganizationResponseDTO;
import com.alkemy.ong.model.Organization;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.service.OrganizationService;
import com.alkemy.ong.service.mapper.OrganizationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Locale;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationMapper organizationMapper;
    @Autowired
    private OrganizationRepository organizationRepository;
    @Autowired
    private MessageSource messageSource;

    public OrganizationResponseDTO getOrganizationPublic() {
        Organization organization = organizationRepository
                .findFirstByOrderById()
                .orElseThrow(() -> new EntityNotFoundException("Organization not found"));
        return organizationMapper.entityToResponseDTO(organization);
    }


    @Transactional
    public OrganizationResponseDTO update(OrganizationRequestDTO organizationRequestDTO) {
        Organization organizationFromDB = organizationRepository
                .findFirstByOrderById()
                .orElseThrow(() -> new EntityNotFoundException(messageSource.getMessage("error.organization.not.present", null, Locale.US)));

        Organization organizationUpdatedToSave = organizationMapper.organizationUpdate(organizationRequestDTO, organizationFromDB);
        return organizationMapper
                .entityToResponseDTO(organizationRepository
                        .save(organizationUpdatedToSave));
    }



}



