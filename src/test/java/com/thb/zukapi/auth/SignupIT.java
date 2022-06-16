package com.thb.zukapi.auth;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.thb.zukapi.ItBase;
import com.thb.zukapi.dtos.person.PersonWriteTO;
import com.thb.zukapi.models.Admin;
import com.thb.zukapi.models.Seeker;
import com.thb.zukapi.transfertobjects.user.SigninTO;

import io.restassured.http.ContentType;

public class SignupIT extends ItBase {

    Seeker seeker, seeker1;
    
    PersonWriteTO signupSeeker;
    
    SigninTO signin;

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
    public void signupSeeker() {
    	signupSeeker = buildSignup();
    	String email = UUID.randomUUID().toString()+"@email.com";
    	signupSeeker.setEmail(email);
        
        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
                        .body(signupSeeker)
                        .log().body()
                        .post("zuk-api/v1/auth/signup")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Seeker seeker = seekerRepository.findById(id).get();

        assertThat(signupSeeker.getLastname(), is(seeker.getLastname()));
        assertThat(signupSeeker.getEmail(), is(seeker.getEmail()));
    }
    
    @Test
    public void signupAdmin() {
    	signupSeeker = buildSignup();
    	signupSeeker.setRole("ADMIN");
    	String email = UUID.randomUUID().toString()+"@email.com";
    	signupSeeker.setEmail(email);
        
        UUID id = UUID.fromString(
                given()
                        .contentType(ContentType.JSON)
                        .body(signupSeeker)
                        .log().body()
                        .post("zuk-api/v1/auth/signup")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("id"));

        Admin admin = adminRepository.findById(id).get();

        assertThat(signupSeeker.getLastname(), is(admin.getLastname()));
        assertThat(signupSeeker.getEmail(), is(admin.getEmail()));
    }
    
    @Test
    public void signinSeeker() throws InterruptedException {
    	
    	signupSeeker = buildSignup();
    	String email = UUID.randomUUID().toString()+"@email.com";
    	signupSeeker.setEmail(email);
    	signupSeeker.setRole("SEEKER");
        
        given()
                .contentType(ContentType.JSON)
                .body(signupSeeker)
                .log().body()
                .post("zuk-api/v1/auth/signup")
                .then()
                .log().body()
                .statusCode(200);
        
        Thread.sleep(2000);
        
        signin = buildSignin();
        signin.setEmail(email);
        
        String email_ = 
                given()
                        .contentType(ContentType.JSON)
                        .body(signin)
                        .log().body()
                        .post("zuk-api/v1/auth/signin")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("email");

        Seeker seeker = seekerRepository.findByEmail(email_).get();

        assertThat(signupSeeker.getLastname(), is(seeker.getLastname()));
        assertThat(signupSeeker.getEmail(), is(seeker.getEmail()));
    } 
    
    @Test
    public void signinAdmin() throws InterruptedException {
    	
    	signupSeeker = buildSignup();
    	signupSeeker.setRole("ADMIN");
        
                given()
                        .contentType(ContentType.JSON)
                        .body(signupSeeker)
                        .log().body()
                        .post("zuk-api/v1/auth/signup")
                        .then()
                        .log().body()
                        .statusCode(200);
        
        Thread.sleep(1000);
        
        signin = buildSignin();
        
        String email = 
                given()
                        .contentType(ContentType.JSON)
                        .body(signin)
                        .log().body()
                        .post("zuk-api/v1/auth/signin")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .extract().body().path("email");

        Admin admin = adminRepository.findByEmail(email).get();

        assertThat(signupSeeker.getLastname(), is(admin.getLastname()));
        assertThat(signupSeeker.getEmail(), is(admin.getEmail()));
    } 

}
