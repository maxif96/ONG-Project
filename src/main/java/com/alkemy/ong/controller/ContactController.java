package com.alkemy.ong.controller;

import com.alkemy.ong.dto.ContactRequestDTO;
import com.alkemy.ong.dto.response.ContactResponseDTO;
import com.alkemy.ong.exception.EmptyListException;
import com.alkemy.ong.service.ContactService;
import java.io.IOException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/contacts")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @PostMapping
    public ResponseEntity<ContactResponseDTO> create(@Valid @RequestBody ContactRequestDTO contactRequestDTO) throws IOException{
        return ResponseEntity.status(HttpStatus.CREATED).body(contactService.createContact(contactRequestDTO));
    }

    @GetMapping
    public ResponseEntity<List<ContactResponseDTO>> getAllContacts () throws EmptyListException {
        List<ContactResponseDTO> contactsResponse = contactService.searchAllContacts();
        return ResponseEntity.ok().body(contactsResponse);
    }

}