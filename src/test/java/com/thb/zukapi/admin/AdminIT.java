package com.thb.zukapi.admin;

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
import com.thb.zukapi.models.Admin;

import io.restassured.http.ContentType;

public class AdminIT extends ItBase {

    Admin admin, admin1;
    
    PersonWriteTO signupAdmin;
        


    @BeforeEach
    public void setup() {
        super.setup();

        
        admin = buildAdmin(user);
        admin = adminRepository.save(admin);

        admin1 = buildAdmin(user1);
        admin1 = adminRepository.save(admin1);

    }

    @AfterEach
    public void cleanup() {
        super.cleanup();
    }


    @Test
    public void createAdmin() {
    	signupAdmin = buildSignup();
    	
        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
                        .body(signupAdmin)
                        .log().body()
                        .post("zuk-api/v1/admin")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Admin admin = adminRepository.findById(id).get();

        assertThat(signupAdmin.getLastname(), is(admin.getLastname()));
        assertThat(signupAdmin.getEmail(), is(admin.getEmail()));
    }
    
    @Test
    public void updateAdmin() {
    	
    	String name = UUID.randomUUID().toString();
    	
    	admin.setFirstname(name);
    	
        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
                        .body(admin)
                        .log().body()
                        .put("zuk-api/v1/admin/")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Admin admin_ = adminRepository.findById(id).get();

        assertThat(admin_.getFirstname(), is(name));
        assertThat(admin_.getLastname(), is(admin.getLastname()));
        assertThat(admin_.getEmail(), is(admin.getEmail()));
    }
    
    @Test
    public void getAdmin() {

        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
                        .log().body()
                        .get("zuk-api/v1/admin/"+admin.getId())
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Admin admin = adminRepository.findById(id).get();

        assertThat(admin.getLastname(), is(admin.getLastname()));
        assertThat(admin.getEmail(), is(admin.getEmail()));
    }
    
    @Test
    public void deleteAdmin() {

        given()
                .contentType(ContentType.JSON)
                .log().body()
                .delete("zuk-api/v1/admin/"+admin.getId())
                .then()
                .log().body()
                .statusCode(200);

        Optional<Admin> admin_ = adminRepository.findById(admin.getId());

        assertThat(admin_.isPresent(), is(false));
    }
}
