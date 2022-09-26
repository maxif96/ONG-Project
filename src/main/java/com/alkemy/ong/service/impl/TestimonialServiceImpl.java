package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.TestimonialRequestDTO;
import com.alkemy.ong.dto.response.TestimonialPageResponse;
import com.alkemy.ong.dto.response.TestimonialResponseDTO;
import com.alkemy.ong.model.Testimonial;
import com.alkemy.ong.repository.TestimonialRepository;
import com.alkemy.ong.service.TestimonialService;
import com.alkemy.ong.service.mapper.TestimonialMapper;
import com.alkemy.ong.util.PaginationUtil;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.Locale;
import java.util.Optional;

@Service
public class TestimonialServiceImpl extends PaginationUtil<Testimonial, Long, TestimonialRepository> implements TestimonialService {

    @Autowired
    private TestimonialMapper testimonialMapper;

    @Autowired
    private MessageSource messageSource;

    @Override
    public TestimonialResponseDTO createTestimonial(TestimonialRequestDTO testimonialRequestDTO) {
        Testimonial testimonial = testimonialMapper.requestDTOToEntity(testimonialRequestDTO);
        return testimonialMapper.entityToResponseDTO(testimonial);
    }

    @Override
    public TestimonialResponseDTO updateTestimonial(TestimonialRequestDTO testimonialRequestDTO, Long id) {
        Testimonial testimonial = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Testimonial with this id not found."));

        Testimonial testimonialToUpdate = testimonialMapper.updateTestimonial(testimonialRequestDTO, testimonial);
        return testimonialMapper.entityToResponseDTO(repository.save(testimonialToUpdate));
    }

    @Transactional(readOnly = true)
    public TestimonialPageResponse pagination(Integer numberPage) throws EntityNotFoundException {
        Page<Testimonial> page = getPage(numberPage);
        validatePageNumber(page, numberPage);

        String previousUrl = urlGetPrevious(numberPage);
        String nextUrl = urlGetNext(page, numberPage);

        return testimonialMapper.buildPage(page.getContent(), previousUrl, nextUrl);
    }

    @Override
    public void deleteTestimonial(Long id) {
        Optional<Testimonial> response = repository.findById(id);
        if (response.isPresent()) {
            repository.deleteById(id);
        } else {
            throw new EntityNotFoundException(messageSource.getMessage("testimonial.notFound", null, Locale.US));
        }
    }

    public void validatePageNumber (Page<?> page, Integer numberPage){
        if (page.getTotalPages() < numberPage) throw new EntityNotFoundException("Page does not have elements.");
    }

}
