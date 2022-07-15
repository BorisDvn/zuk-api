package com.thb.zukapi.seeker;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.thb.zukapi.ItBase;
import com.thb.zukapi.dtos.person.PersonWriteTO;
import com.thb.zukapi.models.Announcement;
import com.thb.zukapi.models.Category;
import com.thb.zukapi.models.File;
import com.thb.zukapi.models.Helper;
import com.thb.zukapi.models.Seeker;

import io.restassured.http.ContentType;

public class SeekerIT extends ItBase {

    Seeker seeker, seeker1;
    
    PersonWriteTO signupSeeker;
    
    Announcement announcement;
    
    Category category;
    
    File file;
        
    Helper helper;

    @BeforeEach
    public void setup() {
        super.setup();
        
        file = buildFile();
        file = fileRepository.save(file);
        
        category = buildCategory(file);
        category = categoryRepository.save(category);
        
        helper = buildHelper(user);
        helper = helperRepository.save(helper);
        
        announcement = buildAnnouncement(helper, category);
        announcement = announcementRepository.save(announcement);
         
        seeker = buildSeekerWithAnnouncement(user, announcement);
        seeker = seekerRepository.save(seeker);

        seeker1 = buildSeeker(user1);
        seeker1 = seekerRepository.save(seeker1);

    }

    @AfterEach
    public void cleanup() {
        super.cleanup();
    }


    @Test
    public void createSeeker() {
    	signupSeeker = buildSignup();
    	
        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
                        .body(signupSeeker)
                        .log().body()
                        .post("zuk-api/v1/seeker")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Seeker seeker = seekerRepository.findById(id).get();

        assertThat(signupSeeker.getLastname(), is(seeker.getLastname()));
        assertThat(signupSeeker.getEmail(), is(seeker.getEmail()));
    }
    
    @Test
    public void updateSeeker() {
    	PersonWriteTO update = buildSignup();
    	update.setId(seeker.getId());
    	update.setAddress(UUID.randomUUID().toString());
    	
        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
                        .body(update)
                        .log().body()
                        .put("zuk-api/v1/seeker")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Seeker seeker = seekerRepository.findById(id).get();

        assertThat(seeker.getId(), is(update.getId()));
        assertThat(seeker.getLastname(), is(update.getLastname()));
        assertThat(seeker.getEmail(), is(update.getEmail()));
    }
    
    @Test
    public void getSeeker() {
    	
    	seeker.getAnnouncements().add(announcement);
    	seekerRepository.save(seeker);

        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
                        .log().body()
                        .get("zuk-api/v1/seeker/"+seeker.getId())
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Seeker seeker_ = seekerRepository.findById(id).get();
        System.out.println(seeker_.getAnnouncements());
        System.out.println(seeker);

        assertThat(seeker_.getLastname(), is(seeker.getLastname()));
        assertThat(seeker_.getEmail(), is(seeker.getEmail()));
    }
    
    @Test
    public void deleteSeeker() {

        given()
                .contentType(ContentType.JSON)
                .log().body()
                .delete("zuk-api/v1/seeker/"+seeker.getId())
                .then()
                .log().body()
                .statusCode(200);

        Optional<Seeker> seeker_ = seekerRepository.findById(seeker.getId());

        assertThat(seeker_.isPresent(), is(false));
    }
}
