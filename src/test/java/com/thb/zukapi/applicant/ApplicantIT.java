package com.thb.zukapi.applicant;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.thb.zukapi.ItBase;
import com.thb.zukapi.dtos.applicants.ApplicantReadTO;
import com.thb.zukapi.dtos.files.FileTO;
import com.thb.zukapi.models.Announcement;
import com.thb.zukapi.models.Applicant;
import com.thb.zukapi.models.Category;
import com.thb.zukapi.models.ContactStatus;
import com.thb.zukapi.models.File;
import com.thb.zukapi.models.Helper;
import com.thb.zukapi.models.Seeker;

import io.restassured.http.ContentType;

public class ApplicantIT extends ItBase {

    Applicant applicant, applicant1, applicant2;
            
    Announcement announcement, announcement1;
    
    Category category, category1;
    
    File file;
    
    FileTO fileTO;
    
    Seeker seeker, seeker1;
    
    Helper helper, helper1;

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
        
        seeker = buildSeeker(user);
        seeker = seekerRepository.save(seeker);
        
        applicant = buildApplicant(seeker, announcement);
        applicant = applicantRepository.save(applicant);

        applicant1 = buildApplicant(seeker, announcement);
        applicant1 = applicantRepository.save(applicant1);
        
        applicant2 = buildApplicant(announcement);
        applicant2 = applicantRepository.save(applicant2);
        
        fileTO = buildFileTO();
        
        when(fileUpload.uploadToFileService(any(), any())).thenReturn(fileTO);

    }

    @AfterEach
    public void cleanup() {
        super.cleanup();
    }

    @Test
    public void createApplicant() {
    	ApplicantReadTO toCreate = buildApplicantReadTO(announcement.getId());
    	    	
        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
                        .body(toCreate)
                        .log().body()
                        .post("zuk-api/v1/applicant")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Applicant applicant_ = applicantRepository.findById(id).get();

        assertThat(applicant_.getDetails(), is(toCreate.getDetails()));
        assertThat(applicant_.getAnnouncement().getId(), is(announcement.getId()));
        assertThat(applicant_.getStatus(), is(toCreate.getStatus()));
        assertThat(applicant_.getPhone(), is(toCreate.getPhone()));
        assertThat(applicant_.getName(), is(toCreate.getName()));
        assertThat(applicant_.getEmail(), is(toCreate.getEmail()));
    }

    @Test
    public void createApplicantSeeker() {
    	ApplicantReadTO toCreate = buildApplicantReadTO(seeker.getId(), announcement.getId());
    	    	
        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
                        .body(toCreate)
                        .log().body()
                        .post("zuk-api/v1/applicant")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Applicant applicant_ = applicantRepository.findById(id).get();

        assertThat(applicant_.getDetails(), is(toCreate.getDetails()));
        assertThat(applicant_.getAnnouncement().getId(), is(announcement.getId()));
        assertThat(applicant_.getSeeker().getId(), is(seeker.getId()));
    }

    @Test
    public void getAllApplicant() {

                given()
                        .contentType(ContentType.JSON)
                        .log().body()
                        .get("zuk-api/v1/applicant")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .body("id", containsInAnyOrder(
                        		applicant.getId().toString(),
                        		applicant1.getId().toString(),
                        		applicant2.getId().toString()
                        		)
                        	);

    }
    
    @Test
    public void getApplicant() {

        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
                        .log().body()
                        .get("zuk-api/v1/applicant/"+applicant.getId())
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Applicant applicant_ = applicantRepository.findById(id).get();

        assertThat(applicant_.getId(), is(applicant.getId()));
        assertThat(applicant_.getDetails(), is(applicant.getDetails()));
        assertThat(applicant_.getAnnouncement().getId(), is(announcement.getId()));
        assertThat(applicant_.getSeeker().getId(), is(seeker.getId()));
    }
    
    @Test
    public void getApplicantByAnnouncementId() {

                given()
                        .contentType(ContentType.JSON)
                        .log().body()
                        .get("zuk-api/v1/applicant/announcement/"+announcement.getId())
                        .then()
                        .log().body()
                        .statusCode(200)
                        .body("id", containsInAnyOrder(
                        		applicant.getId().toString(),
                        		applicant1.getId().toString(),
                        		applicant2.getId().toString()
                        		)
                        	);
    }
    
    @Test
    public void getApplicantBySeekerEmail() {

                given()
                        .contentType(ContentType.JSON)
                        .log().body()
                        .queryParam("email", seeker.getEmail())
                        .get("zuk-api/v1/applicant/seeker")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .body("id", containsInAnyOrder(applicant.getId().toString(), applicant1.getId().toString()));
    }
    
    @Test
    public void getApplicantByEmail() {

                given()
                        .contentType(ContentType.JSON)
                        .log().body()
                        .queryParam("email", seeker.getEmail())
                        .get("zuk-api/v1/applicant/email")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .body("id", containsInAnyOrder(applicant1.getId().toString(), applicant.getId().toString()));
    }
    
    @Test
    public void updateApplicant() {
    	
    	String newDetails = UUID.randomUUID().toString();
    	
    	ApplicantReadTO toCreate = buildApplicantReadTO(seeker.getId(), announcement.getId());
    	toCreate.setDetails(newDetails);
    	toCreate.setId(applicant.getId());
    	toCreate.setStatus(ContactStatus.READ);
    	
        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
                        .body(toCreate)
                        .log().body()
                        .put("zuk-api/v1/applicant")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Applicant applicant_ = applicantRepository.findById(id).get();

        assertThat(applicant_.getId(), is(applicant.getId()));
        assertThat(applicant_.getDetails(), is(newDetails));
        assertThat(applicant_.getStatus(), is(ContactStatus.READ));
    }
    
    @Test
    public void deleteApplicant() {

        given()
                .contentType(ContentType.JSON)
                .log().body()
                .delete("zuk-api/v1/applicant/"+applicant.getId())
                .then()
                .log().body()
                .statusCode(200);

        Optional<Applicant> applicant_ = applicantRepository.findById(applicant.getId());
        assertThat(applicant_.isPresent(), is(false));
    }
}
