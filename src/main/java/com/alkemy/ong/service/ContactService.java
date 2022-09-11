package com.alkemy.ong.service;

import com.alkemy.ong.dto.ContactRequestDTO;
import com.alkemy.ong.dto.response.ContactResponseDTO;
import com.alkemy.ong.exception.EmptyListException;

import java.io.IOException;
import java.util.List;

public interface ContactService {


    ContactResponseDTO createContact(ContactRequestDTO contactRequestDTO) throws IOException;

    List<ContactResponseDTO> searchAllContacts() throws EmptyListException;

}
