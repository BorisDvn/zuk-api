package com.thb.zukapi.announcement;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.hamcrest.Matchers.containsInAnyOrder;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.thb.zukapi.ItBase;
import com.thb.zukapi.dtos.announcements.AnnouncementWriteTO;
import com.thb.zukapi.dtos.files.FileTO;
import com.thb.zukapi.models.Announcement;
import com.thb.zukapi.models.AnnouncementStatus;
import com.thb.zukapi.models.AnnouncementStype;
import com.thb.zukapi.models.Category;
import com.thb.zukapi.models.File;
import com.thb.zukapi.models.Helper;

import io.restassured.http.ContentType;

public class AnnouncementIT extends ItBase {

    Announcement announcement, announcement1;
            
    Category category, category1;
    
    File file;
    
    FileTO fileTO;
    
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
        announcement.setType(AnnouncementStype.NEED);
        announcement = announcementRepository.save(announcement);

        announcement1 = buildAnnouncement(helper, category);
        announcement1.setType(AnnouncementStype.OFFER);
        announcement1 = announcementRepository.save(announcement1);
        
        fileTO = buildFileTO();
        
        when(fileUpload.uploadToFileService(any(), any())).thenReturn(fileTO);

    }

    @AfterEach
    public void cleanup() {
        super.cleanup();
    }


//    @Test
//    public void createAnnouncement() {
//    	AnnouncementWriteTO toCreate = buildAnnouncementWriteTO(helper.getId(), category.getId());
//    	
//        UUID id = UUID.fromString(
//                given()
//                        .contentType(ContentType.JSON)
//                        .multiPart("announcement", toCreate,"application/json")
//                        .multiPart("file", new MockMultipartFile("foo", "foo.txt", MediaType.TEXT_PLAIN_VALUE,
//                        	      "Hello World".getBytes()))
////                        .body()
//                        .log().body()
//                        .post("zuk-api/v1/announcement")
//                        .then()
//                        .log().body()
//                        .statusCode(200)
//                        .extract().body().path("id"));
//
//        Announcement announcement = announcementRepository.findById(id).get();
//
//        assertThat(toCreate.getTitle(), is(announcement.getTitle()));
//    }

    @Test
    public void getAllAnnouncement() {

                given()
                        .contentType(ContentType.JSON)
                        .log().body()
                        .get("zuk-api/v1/announcement")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .body("id", containsInAnyOrder(announcement.getId().toString(), announcement1.getId().toString()));

    }
    
    @Test
    public void getAnnouncement() {

        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
                        .log().body()
                        .get("zuk-api/v1/announcement/"+announcement.getId())
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Announcement announcement_ = announcementRepository.findById(id).get();

        assertThat(announcement_.getTitle(), is(announcement.getTitle()));
        assertThat(announcement_.getDescription(), is(announcement.getDescription()));
    }
    
    @Test
    public void getAnnouncementByCategory() {

                given()
                        .contentType(ContentType.JSON)
                        .log().body()
                        .get("zuk-api/v1/announcement/category/"+category.getName())
                        .then()
                        .log().body()
                        .statusCode(200)
                        .body("id", containsInAnyOrder(announcement.getId().toString(), announcement1.getId().toString()));
    }
    
    @Test
    public void getAnnouncementByType() {

                given()
                        .contentType(ContentType.JSON)
                        .log().body()
                        .param("type", "NEED")
                        .get("zuk-api/v1/announcement/type")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .body("id", containsInAnyOrder(announcement.getId().toString()));
    }
    
    @Test
    public void getAnnouncementByType__() {

                given()
                        .contentType(ContentType.JSON)
                        .log().body()
                        .param("type", "OFFER")
                        .get("zuk-api/v1/announcement/type")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .body("id", containsInAnyOrder(announcement1.getId().toString()));
    }
    
    @Test
    public void getAnnouncementByUserEmail() {

                given()
                        .contentType(ContentType.JSON)
                        .log().body()
                        .param("email", helper.getEmail())
                        .param("type", "NEED")
                        .get("zuk-api/v1/announcement/user")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .body("id", containsInAnyOrder(announcement.getId().toString(), announcement1.getId().toString()));
    }
    
    @Test
    public void updateAnnouncement() {
    	
    	String newTitle = UUID.randomUUID().toString();
    	
    	AnnouncementWriteTO toCreate = buildAnnouncementWriteTO(helper.getId(), category.getId());
    	toCreate.setTitle(newTitle);
    	toCreate.setId(announcement.getId());
    	toCreate.setStatus(AnnouncementStatus.PROCESSED);
    	
        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
                        .body(toCreate)
                        .log().body()
                        .put("zuk-api/v1/announcement")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Announcement announcement_ = announcementRepository.findById(id).get();

        assertThat(announcement_.getTitle(), is(newTitle));
        assertThat(announcement_.getDescription(), is(toCreate.getDescription()));
    }
    
    @Test
    public void deleteAnnouncement() {

        given()
                .contentType(ContentType.JSON)
                .log().body()
                .delete("zuk-api/v1/announcement/"+announcement.getId())
                .then()
                .log().body()
                .statusCode(200);

        Optional<Announcement> announcement_ = announcementRepository.findById(announcement.getId());
        assertThat(announcement_.isPresent(), is(false));
    }
}
