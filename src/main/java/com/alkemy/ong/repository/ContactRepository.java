package com.alkemy.ong.repository;

import com.alkemy.ong.model.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Integer> {

    Boolean existsByName(String name);
    Boolean existsByPhone(Integer phone);
    Boolean existsByEmail(String email);
}
