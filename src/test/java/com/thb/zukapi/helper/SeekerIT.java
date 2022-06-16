package com.thb.zukapi.helper;

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
import com.thb.zukapi.models.Seeker;

import io.restassured.http.ContentType;

public class SeekerIT extends ItBase {

    Seeker seeker, seeker1;
    
    PersonWriteTO signupSeeker;

    @BeforeEach
    public void setup() {
        super.setup();

        seeker = buildSeeker();
        seeker = seekerRepository.save(seeker);

        seeker1 = buildSeeker();
        seeker1 = seekerRepository.save(seeker1);

    }

    @AfterEach
    public void cleanup() {
        super.cleanup();
    }


    @Test // TODO check why the test fail
    public void createSeeker() {
    	signupSeeker = buildSignup();
    	
    	System.out.println(signupSeeker);

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
    public void getSeeker() {

        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
                        .log().body()
                        .get("zuk-api/v1/seeker/"+seeker.getId())
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Seeker seeker = seekerRepository.findById(id).get();

        assertThat(seeker.getLastname(), is(seeker.getLastname()));
        assertThat(seeker.getEmail(), is(seeker.getEmail()));
    }
    
    @Test
    public void deleteSeeker() {

        given()
                .contentType(ContentType.JSON)
                .log().body()
                .get("zuk-api/v1/seeker/"+seeker.getId())
                .then()
                .log().body()
                .statusCode(200);

        Optional<Seeker> seeker_ = seekerRepository.findById(seeker.getId());

        assertThat(seeker_.isPresent(), is(false));
    }
}
