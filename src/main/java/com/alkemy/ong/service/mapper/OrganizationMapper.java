package com.alkemy.ong.service.mapper;

import com.alkemy.ong.dto.OrganizationRequestDTO;
import com.alkemy.ong.dto.response.OrganizationResponseDTO;
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

    public OrganizationResponseDTO entityToResponseDTO(Organization organization){
        return OrganizationResponseDTO
                .builder()
                .id(organization.getId())
                .name(organization.getEmail())
                .image(organization.getImage())
                .address(organization.getAddress())
                .phone(organization.getPhone())
                .urlFacebook(organization.getUrlFacebook())
                .urlLinkedin(organization.getUrlLinkedin())
                .urlInstagram(organization.getUrlInstagram())
                .createdAt(organization.getCreationTimestamp())
                .updatedAt(organization.getUpdateTimestamp())
                .slides(organization.getSlides().stream()
                        .map(x -> slideMapper.entityToDTO(x)).collect(Collectors.toList()))
                .build();
    }

    public Organization organizationUpdate (OrganizationRequestDTO organizationDTO, Organization entity){
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
                .creationTimestamp(entity.getCreationTimestamp())
                .updateTimestamp(LocalDateTime.now())
                .build();
    }
}