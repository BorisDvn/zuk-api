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
import com.thb.zukapi.models.Helper;

import io.restassured.http.ContentType;

public class HelperIT extends ItBase {

    Helper helper, helper1;
    
    PersonWriteTO signupHelper;
        


    @BeforeEach
    public void setup() {
        super.setup();

        
        helper = buildHelper(user);
        helper = helperRepository.save(helper);

        helper1 = buildHelper(user1);
        helper1 = helperRepository.save(helper1);

    }

    @AfterEach
    public void cleanup() {
        super.cleanup();
    }


    @Test
    public void createHelper() {
    	signupHelper = buildSignup();
    	
    	System.out.println(signupHelper);

        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
                        .body(signupHelper)
                        .log().body()
                        .post("zuk-api/v1/helper")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Helper helper = helperRepository.findById(id).get();

        assertThat(signupHelper.getLastname(), is(helper.getLastname()));
        assertThat(signupHelper.getEmail(), is(helper.getEmail()));
    }
    
    @Test
    public void getHelper() {

        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
                        .log().body()
                        .get("zuk-api/v1/helper/"+helper.getId())
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Helper helper = helperRepository.findById(id).get();

        assertThat(helper.getLastname(), is(helper.getLastname()));
        assertThat(helper.getEmail(), is(helper.getEmail()));
    }
    
    @Test
    public void deleteHelper() {

        given()
                .contentType(ContentType.JSON)
                .log().body()
                .get("zuk-api/v1/helper/"+helper.getId())
                .then()
                .log().body()
                .statusCode(200);

        Optional<Helper> helper_ = helperRepository.findById(helper.getId());

        assertThat(helper_.isPresent(), is(false));
    }
}
