package com.alkemy.ong.service.impl;

import com.alkemy.ong.dto.ContactRequestDTO;
import com.alkemy.ong.dto.response.ContactResponseDTO;
import com.alkemy.ong.exception.EmptyListException;
import com.alkemy.ong.model.Contact;
import com.alkemy.ong.repository.ContactRepository;
import com.alkemy.ong.service.ContactService;
import com.alkemy.ong.service.MailService;
import com.alkemy.ong.service.mapper.contact.ContactMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static java.util.stream.Collectors.toList;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ContactMapper contactMapper;
    @Autowired
    private MailService mailService;
    @Autowired
    private MessageSource messageSource;

    public ContactResponseDTO createContact(ContactRequestDTO contactRequestDTO) throws IOException {
        Contact contact = contactMapper.requestDTOToEntity(contactRequestDTO);
        Contact newContact = contactRepository.save(contact);
        try {
            mailService.sendEmailCreatedContact(newContact.getEmail());
        } catch (IOException ex) {
            throw new IOException(messageSource.getMessage("error.sendMail.notFound", null, Locale.US));
        }
        return contactMapper.entityToResponseDTO(newContact);
    }

    public List<ContactResponseDTO> searchAllContacts() throws EmptyListException {
        if (contactRepository.count() < 1) throw new EmptyListException("No one contact was found.");
        return contactRepository.findAll().stream()
                .map(x -> contactMapper.entityToResponseDTO(x))
                .collect(toList());
    }


}