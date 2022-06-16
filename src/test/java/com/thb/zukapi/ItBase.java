package com.thb.zukapi;

import java.time.LocalDate;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.thb.zukapi.dtos.person.PersonWriteTO;
import com.thb.zukapi.models.Gender;
import com.thb.zukapi.models.Seeker;
import com.thb.zukapi.repositories.AdminRepository;
import com.thb.zukapi.repositories.SeekerRepository;
import com.thb.zukapi.transfertobjects.user.SigninTO;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-testit.yml")
@ActiveProfiles("testit")
public class ItBase {

	protected MockMvc mockMvc;

	protected MockHttpSession session;

	@Autowired
	protected SeekerRepository seekerRepository;

	@Autowired
	protected AdminRepository adminRepository;

	@Autowired
	private WebApplicationContext wac;

	@BeforeEach
	public void setup() {
		this.session = new MockHttpSession();
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		RestAssuredMockMvc.webAppContextSetup(wac);
	}

	public void cleanup() {

		seekerRepository.deleteAll();
		adminRepository.deleteAll();

	}

	protected Seeker buildSeeker() {

		Seeker seeker = new Seeker();
		seeker.setAdresse(UUID.randomUUID().toString());
		seeker.setDob(LocalDate.now());
		seeker.setEmail(UUID.randomUUID().toString() + "@email.com");
		seeker.setFirstname(UUID.randomUUID().toString());
		seeker.setLastname(UUID.randomUUID().toString());
		seeker.setGender(Gender.M);
		seeker.setPhone("+0012334234543");

		return seeker;
	}

	protected PersonWriteTO buildSignup() {

		PersonWriteTO seeker = new PersonWriteTO();
		seeker.setAdresse(UUID.randomUUID().toString());
		seeker.setDob(LocalDate.now());
		seeker.setEmail("123456@email.com");
		seeker.setFirstname(UUID.randomUUID().toString());
		seeker.setLastname(UUID.randomUUID().toString());
		seeker.setGender(Gender.M);
		seeker.setRole("SEEKER");
		seeker.setPassword("123456789");
		seeker.setPhone("+0012334234543");

		return seeker;
	}
	
	protected SigninTO buildSignin() {

		SigninTO seeker = new SigninTO();
		seeker.setEmail("123456@email.com");
		seeker.setPassword("123456789");

		return seeker;
	}

}
