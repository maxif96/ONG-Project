package com.alkemy.ong.service.mapper;

import com.alkemy.ong.dto.OrganizationRequestDTO;
import com.alkemy.ong.dto.response.OrganizationResponseDTO;
import com.alkemy.ong.exception.EmptyListException;
import com.alkemy.ong.model.Organization;
import com.alkemy.ong.repository.SlideRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Component
public class OrganizationMapper {

    @Autowired
    private SlideMapper slideMapper;
    @Autowired
    private SlideRepository slideRepository;

    public OrganizationResponseDTO entityToResponseDTO(Organization organization) throws EmptyListException {
        return OrganizationResponseDTO
                .builder()
                .id(organization.getId())
                .name(organization.getName())
                .email(organization.getEmail())
                .image(organization.getImage())
                .address(organization.getAddress())
                .phone(organization.getPhone())
                .aboutUsText(organization.getAboutUsText())
                .welcomeText(organization.getWelcomeText())
                .urlFacebook(organization.getUrlFacebook())
                .urlLinkedin(organization.getUrlLinkedin())
                .urlInstagram(organization.getUrlInstagram())
                .createdAt(organization.getCreationTimestamp())
                .updatedAt(organization.getUpdateTimestamp())
                .slides(organization.getSlides().stream()
                        .map(x -> slideMapper.entityToResponseDTO(x)).collect(Collectors.toList()))
                .build();
    }

    public Organization organizationUpdate(OrganizationRequestDTO organizationDTO, Organization entity) {
        return Organization.builder()
                .id(entity.getId())
                .name(organizationDTO.getName())
                .address(organizationDTO.getAddress())
                .phone(organizationDTO.getPhone())
                .email(organizationDTO.getEmail())
                .image(organizationDTO.getImage())
                .aboutUsText(organizationDTO.getAboutUsText())
                .welcomeText(organizationDTO.getWelcomeText())
                .urlFacebook(organizationDTO.getUrlFacebook())
                .urlInstagram(organizationDTO.getUrlInstagram())
                .urlLinkedin(organizationDTO.getUrlLinkedin())
                .slides(entity.getSlides())
                .creationTimestamp(entity.getCreationTimestamp())
                .updateTimestamp(LocalDateTime.now())
                .build();
    }
}