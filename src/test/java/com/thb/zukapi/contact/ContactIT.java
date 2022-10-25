package com.thb.zukapi.contact;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.thb.zukapi.ItBase;
import com.thb.zukapi.models.Contact;

import io.restassured.http.ContentType;

public class ContactIT extends ItBase {

    Contact contact, contact1;
    

    @BeforeEach
    public void setup() {
        super.setup();

        contact = buildContact();
        contact = contactRepository.save(contact);

        contact1 = buildContact();
        contact1 = contactRepository.save(contact1);

    }

    @AfterEach
    public void cleanup() {
        super.cleanup();
    }


    @Test
    public void createContact() {
    	contact = buildContact();

        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
                        .body(contact)
                        .log().body()
                        .post("zuk-api/v1/contact")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Contact contact_ = contactRepository.findById(id).get();
        
        assertThat(contact.getSubject(), is(contact_.getSubject()));
        assertThat(contact.getDescription(), is(contact_.getDescription()));
        assertThat(contact.getStatus(), is(contact_.getStatus()));

    }
    
    @Test
    public void updateContact() {
    	contact = buildContact();
    	String subject = UUID.randomUUID().toString();
    	contact.setSubject(subject);

        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
                        .body(contact)
                        .log().body()
                        .post("zuk-api/v1/contact")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Contact contact_ = contactRepository.findById(id).get();
        
        assertThat(contact.getSubject(), is(subject));
        assertThat(contact.getDescription(), is(contact_.getDescription()));
        assertThat(contact.getStatus(), is(contact_.getStatus()));

    }
    
    @Test
    public void getContact() {

        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
                        .log().body()
                        .get("zuk-api/v1/contact/"+contact.getId())
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Contact contact_ = contactRepository.findById(id).get();
        
        assertThat(contact.getSubject(), is(contact_.getSubject()));
        assertThat(contact.getDescription(), is(contact_.getDescription()));
        assertThat(contact.getStatus(), is(contact_.getStatus()));
    }
    
    @Test
    public void deleteContact() {

        given()
                .contentType(ContentType.JSON)
                .log().body()
                .delete("zuk-api/v1/contact/"+contact.getId())
                .then()
                .log().body()
                .statusCode(200);

        Optional<Contact> contact_ = contactRepository.findById(contact.getId());

        assertThat(contact_.isPresent(), is(false));
    }
}
