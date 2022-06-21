package com.thb.zukapi.controller;

import com.thb.zukapi.models.Contact;
import com.thb.zukapi.services.ContactService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("zuk-api/v1/contact")
@CrossOrigin(origins = "*")
public class ContactController {
    @Autowired
    protected ContactService contactService;

    @Operation(summary = "Get All Contact")
    @ApiResponse(responseCode = "200", description = "Found all Contact",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Contact.class))})
    @GetMapping("")
    public List<Contact> getAllContact(@RequestParam(defaultValue = "0") Integer pageNo,
                                         @RequestParam(defaultValue = "10") Integer pageSize,
                                         @RequestParam(defaultValue = "name") String sortBy) {
        return contactService.getAll(pageNo, pageSize, sortBy);
    }

    @Operation(summary = "Get a Contact by its id")
    @ApiResponse(responseCode = "200", description = "Found the Contact",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Contact.class))})
    @GetMapping("/{id}")
    public Contact getContactById(@Parameter(name = "ContactId", description = "ID of the Contact_obj") @PathVariable UUID id) {
        return contactService.getContact(id);
    }

    @Operation(summary = "Add One Contact")
    @ApiResponse(responseCode = "200", description = "Contact added",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Contact.class))})
    @PostMapping("")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public Contact addContact(
            @Parameter(name = "Contact", description = "Contact_obj to add") @RequestBody Contact contact) {
        return contactService.addContact(contact);
    }

    @Operation(summary = "Update Contact")
    @ApiResponse(responseCode = "200", description = "News Contact",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Contact.class))})
    @PutMapping("")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public Contact updateContact(@Parameter(name = "Contact", description = "Contact_obj to update") @RequestBody Contact contact) {
        return contactService.updateContact(contact);
    }

    @Operation(summary = "Delete a Contact by its id")
    @ApiResponse(responseCode = "200", description = "News Contact",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Contact.class))})
    @DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteContact(@Parameter(name = "ContactId", description = "Id of the Contact to delete") @PathVariable UUID id) {
        return contactService.deleteContactById(id);
    }
}
