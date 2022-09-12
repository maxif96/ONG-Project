package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.SlideRequestDTO;
import com.alkemy.ong.dto.response.SlideResponseDTO;
import com.alkemy.ong.exception.EmptyListException;
import com.alkemy.ong.model.Slide;
import com.alkemy.ong.repository.OrganizationRepository;
import com.alkemy.ong.repository.SlideRepository;
import com.alkemy.ong.service.AmazonService;
import com.alkemy.ong.service.SlideService;
import com.alkemy.ong.service.mapper.SlideMapper;
import com.alkemy.ong.util.Base64ToMultipartFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class SlideServiceImpl implements SlideService {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private OrganizationRepository organizationRepository;

    @Autowired
    private SlideRepository slideRepository;
    @Autowired
    private SlideMapper slideMapper;

    @Autowired
    private AmazonService amazonService;

    @Transactional
    public SlideResponseDTO createSlides(SlideRequestDTO slideRequest) throws Exception {
        try {
            Base64ToMultipartFile decodBase64ToMultipartFile = new Base64ToMultipartFile();
            MultipartFile urlImage = decodBase64ToMultipartFile.base64ToMultipart(slideRequest.getImageUrl());
            String fileUrl = amazonService.uploadFile(urlImage);

            Slide slide = slideMapper.requestDTOToEntity(slideRequest);

            if (slideRequest.getPosition() == null) {
                slide.setPosition(slideRepository.lastPosition() + 1);
            }
            slide.setImageUrl(slide.getImageUrl());
            Slide slideDB = slideRepository.save(slide);

            return slideMapper.entityToResponseDTO(slideDB);
        } catch (Exception e) {
            throw new Exception(messageSource.getMessage("error.created.slide", null, Locale.US));
        }

    }

    @Transactional(readOnly = true)
    public List<SlideResponseDTO> getAllSlides() throws EmptyListException {
        if (slideRepository.count() < 1) throw new EmptyListException("There is not any slide.");
        return slideRepository.
                findAll().stream()
                .map(slide -> slideMapper.entityToResponseDTO(slide))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public SlideResponseDTO getById(Long id) {
        Slide slideFound = slideRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Slide " + messageSource.getMessage("not.found", null, Locale.US)));
        return slideMapper.entityToResponseDTO(slideFound);
    }

    @Transactional(readOnly = true)
    public List<SlideResponseDTO> getAllSlidesById(Long id) throws EmptyListException {
        if (slideRepository.count() < 1) throw new EmptyListException("There is not any slide.");
        return slideRepository
                .findByOrganizationId((id)).stream()
                .map(slide -> slideMapper.entityToResponseDTO(slide))
                .collect(Collectors.toList());
    }

    @Transactional
    public SlideResponseDTO update(Long id, SlideRequestDTO requestDTO) {
        Slide slide = slideRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(messageSource
                        .getMessage("slide.not.found", null, Locale.US)));

        slide = slideMapper.updateSlide(requestDTO, slide);
        Slide slideUpdated = slideRepository.save(slide);

        return slideMapper.entityToResponseDTO(slideUpdated);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!slideRepository.existsById(id)) throw new EntityNotFoundException("Slide with that id was not found.");
        slideRepository.deleteById(id);
    }



}
