package com.alkemy.ong.service.mapper;

import com.alkemy.ong.dto.SlideRequestDTO;
import com.alkemy.ong.dto.response.SlideResponseDTO;
import com.alkemy.ong.model.Slide;
import com.alkemy.ong.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.Locale;

@Component
public class SlideMapper {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private MessageSource messageSource;

    public SlideResponseDTO entityToResponseDTO(Slide slideEntity) {
        if (!organizationRepository.existsById(slideEntity.getOrganization().getId())) throw new EntityNotFoundException("Organization not found.");
        return SlideResponseDTO.builder()
                .id(slideEntity.getId())
                .text(slideEntity.getText())
                .imageUrl(slideEntity.getImageUrl())
                .position(slideEntity.getPosition())
                .organizationId(slideEntity.getOrganization().getId())
                .build();
    }

    public Slide updateSlide(SlideRequestDTO request, Slide entityFromDB) {
        return Slide.builder()
                .id(entityFromDB.getId())
                .imageUrl(request.getImageUrl())
                .text(request.getText())
                .position(request.getPosition())
                .organization(organizationRepository
                        .findFirstByOrderById()
                        .orElseThrow(() -> new EntityNotFoundException(
                                messageSource.getMessage("organization.not.found", null, Locale.US))))
                .build();
    }


    public Slide requestDTOToEntity(SlideRequestDTO slideRequest) {
        return Slide.builder()
                .imageUrl(slideRequest.getImageUrl())
                .position(slideRequest.getPosition())
                .text(slideRequest.getText())
                .organization(organizationRepository.findFirstByOrderById()
                        .orElseThrow(() -> new EntityNotFoundException("Organization not found.")))
                .build();
    }
}
