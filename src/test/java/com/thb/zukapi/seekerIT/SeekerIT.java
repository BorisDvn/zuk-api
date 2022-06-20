package com.thb.zukapi.seekerIT;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.thb.zukapi.ItBase;
import com.thb.zukapi.models.Seeker;

import io.restassured.http.ContentType;

public class SeekerIT extends ItBase {

    Seeker seeker, seeker1;

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


    @Test
    public void createSeeker() {
        Seeker create = buildSeeker();

        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
                        .body(create)
                        .log().body()
                        .post("zuk-api/v1/seeker")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Seeker seeker = seekerRepository.findById(id).get();

        assertThat(create.getLastname(), is(seeker.getLastname()));
        assertThat(create.getEmail(), is(seeker.getEmail()));
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
}
