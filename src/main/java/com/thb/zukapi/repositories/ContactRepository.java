package com.thb.zukapi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thb.zukapi.models.Contact;

@Repository
public interface ContactRepository extends JpaRepository<Contact, UUID> {
}
