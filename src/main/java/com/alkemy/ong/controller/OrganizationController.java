package com.alkemy.ong.controller;

import com.alkemy.ong.dto.OrganizationRequestDTO;
import com.alkemy.ong.dto.response.OrganizationResponseDTO;
import com.alkemy.ong.exception.EmptyListException;
import com.alkemy.ong.service.OrganizationService;
import com.alkemy.ong.service.SlideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/organization")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;
    @Autowired
    private SlideService slideService;
    @Autowired
    private MessageSource messageSource;

    @GetMapping("/public")
    public ResponseEntity<OrganizationResponseDTO> getOrganizationsPublic() throws Exception {
        OrganizationResponseDTO organizationResponseDTO = organizationService.getOrganizationPublic();
        return ResponseEntity.ok().body(organizationResponseDTO);
    }

    @PutMapping("/public")
    public ResponseEntity<OrganizationResponseDTO> update(@Valid @RequestBody OrganizationRequestDTO organizationRequestDTO) throws EmptyListException {
        OrganizationResponseDTO organizationUpdated = organizationService.update(organizationRequestDTO);
        return ResponseEntity.ok().body(organizationUpdated);
    }

}
