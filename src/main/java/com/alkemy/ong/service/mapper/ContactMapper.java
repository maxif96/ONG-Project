package com.alkemy.ong.service.mapper;

import com.alkemy.ong.dto.ContactRequestDTO;
import com.alkemy.ong.dto.response.ContactResponseDTO;
import com.alkemy.ong.model.Contact;
import org.springframework.stereotype.Component;

@Component
public class ContactMapper {

    public ContactResponseDTO entityToResponseDTO(Contact contact) {
        return ContactResponseDTO.builder()
                .idContact(contact.getId())
                .name(contact.getName())
                .email(contact.getEmail())
                .phone(contact.getPhone())
                .message(contact.getMessage())
                .build();
    }

    public Contact requestDTOToEntity(ContactRequestDTO contactRequestDTO) {
        return Contact.builder()
                .name(contactRequestDTO.getName())
                .phone(contactRequestDTO.getPhone())
                .email(contactRequestDTO.getEmail())
                .message(contactRequestDTO.getMessage())
                .build();
    }
}
