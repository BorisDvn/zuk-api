package com.thb.zukapi.services;

import com.thb.zukapi.exception.ApiRequestException;
import com.thb.zukapi.models.Contact;
import com.thb.zukapi.repositories.ContactRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ContactService {

    private final Logger logger = LoggerFactory.getLogger(ContactService.class);

    @Autowired
    private ContactRepository contactRepository;

    public Contact getContact(UUID id) {
        return contactRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Cannot find Contact with id: " + id));
    }

    public List<Contact> getAll(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Contact> pagedResult = contactRepository.findAll(paging);

        return pagedResult.getContent();
    }

    public Contact addContact(Contact contact) {

        Contact newContact = new Contact();

        newContact.setSubject(contact.getSubject());
        newContact.setDescription(contact.getDescription());
        newContact.setStatus(contact.getStatus());

        return contactRepository.save(newContact);
    }

    public Contact updateContact(Contact contact) {

        Contact contactToUpdate = getContact(contact.getId());

        if (contact.getSubject() != null)
            contactToUpdate.setSubject(contact.getSubject());
        if (contact.getDescription() != null)
            contactToUpdate.setDescription(contact.getDescription());
        if (contact.getStatus() != null)
            contactToUpdate.setStatus(contact.getStatus());

        return contactRepository.save(contactToUpdate);
    }

    public ResponseEntity<String> deleteContactById(UUID id) {

        if (getContact(id) != null) {
            contactRepository.deleteById(id);
        }

        logger.info("Contact successfully deleted");
        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }
}
