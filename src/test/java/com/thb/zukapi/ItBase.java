package com.thb.zukapi;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.thb.zukapi.dtos.announcements.AnnouncementWriteTO;
import com.thb.zukapi.dtos.applicants.ApplicantReadTO;
import com.thb.zukapi.dtos.category.CategoryWriteTO;
import com.thb.zukapi.dtos.files.FileTO;
import com.thb.zukapi.dtos.person.PersonWriteTO;
import com.thb.zukapi.dtos.user.SigninTO;
import com.thb.zukapi.models.Admin;
import com.thb.zukapi.models.Announcement;
import com.thb.zukapi.models.AnnouncementStatus;
import com.thb.zukapi.models.Applicant;
import com.thb.zukapi.models.Category;
import com.thb.zukapi.models.Contact;
import com.thb.zukapi.models.ContactStatus;
import com.thb.zukapi.models.File;
import com.thb.zukapi.models.Gender;
import com.thb.zukapi.models.Helper;
import com.thb.zukapi.models.Role;
import com.thb.zukapi.models.RoleType;
import com.thb.zukapi.models.Seeker;
import com.thb.zukapi.models.User;
import com.thb.zukapi.repositories.AdminRepository;
import com.thb.zukapi.repositories.AnnouncementRepository;
import com.thb.zukapi.repositories.ApplicantRepository;
import com.thb.zukapi.repositories.CategoryRepository;
import com.thb.zukapi.repositories.ContactRepository;
import com.thb.zukapi.repositories.FileRepository;
import com.thb.zukapi.repositories.HelperRepository;
import com.thb.zukapi.repositories.ManagerRepository;
import com.thb.zukapi.repositories.RoleRepository;
import com.thb.zukapi.repositories.SeekerRepository;
import com.thb.zukapi.repositories.UserRepository;
import com.thb.zukapi.utils.FileUpload;

import io.restassured.module.mockmvc.RestAssuredMockMvc;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations = "classpath:application-testit.yml")
@ActiveProfiles("testit")
public class ItBase {

	protected MockMvc mockMvc;

	@Autowired
	protected SeekerRepository seekerRepository;

	@Autowired
	protected HelperRepository helperRepository;

	@Autowired
	protected AdminRepository adminRepository;

	@Autowired
	protected ContactRepository contactRepository;

	@Autowired
	protected CategoryRepository categoryRepository;

	@Autowired
	protected UserRepository userRepository;

	@Autowired
	protected ManagerRepository managerRepository;
	
	@Autowired
	protected RoleRepository roleRepository;

	@Autowired
	protected FileRepository fileRepository;

	@Autowired
	protected AnnouncementRepository announcementRepository;

	@Autowired
	protected ApplicantRepository applicantRepository;

	@Mock
	protected FileUpload fileUpload;

	@Autowired
	private WebApplicationContext wac;

	protected User user, user1;

	protected Role role, role1;

	@BeforeEach
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
		RestAssuredMockMvc.webAppContextSetup(wac);

		// build some system users and roles
		user = userRepository.save(buildUser(roleRepository.findByName(RoleType.HELPER).get()));
		user1 = userRepository.save(buildUser(roleRepository.findByName(RoleType.ADMIN).get()));
	}

	public void cleanup() {

		adminRepository.deleteAll();
		applicantRepository.deleteAll();
		seekerRepository.deleteAll();
		announcementRepository.deleteAll();
		helperRepository.deleteAll();
		categoryRepository.deleteAll();
		fileRepository.deleteAll();
		managerRepository.deleteAll();
		userRepository.deleteAll();
		contactRepository.deleteAll();
	}

	protected Seeker buildSeeker(User user) {

		Seeker seeker = new Seeker();
		seeker.setAddress(UUID.randomUUID().toString());
		seeker.setDob(LocalDate.now());
		seeker.setEmail(UUID.randomUUID().toString() + "@email.com");
		seeker.setFirstname(UUID.randomUUID().toString());
		seeker.setLastname(UUID.randomUUID().toString());
		seeker.setGender(Gender.M);
		seeker.setPhone("+0012334234543");
		seeker.setUser(user);

		return seeker;
	}

	protected Seeker buildSeekerWithAnnouncement(User user, Announcement announcemn) {

		Seeker seeker = buildSeeker(user);
		seeker.getAnnouncements().add(announcemn);

		return seeker;
	}

	protected User buildUser(Role role) {

		User user = new User();
		user.setPassword(UUID.randomUUID().toString());
		user.setEmail(UUID.randomUUID().toString() + "@email.com");
		Set<Role> roles = new HashSet<>();
		roles.add(role);
		user.setRoles(roles);

		return user;
	}

	protected Helper buildHelper(User user) {

		Helper helper = new Helper();
		helper.setAddress(UUID.randomUUID().toString());
		helper.setDob(LocalDate.now());
		helper.setEmail(UUID.randomUUID().toString() + "@email.com");
		helper.setFirstname(UUID.randomUUID().toString());
		helper.setLastname(UUID.randomUUID().toString());
		helper.setGender(Gender.M);
		helper.setPhone("+0012334234543");
		helper.setUser(user);

		return helper;
	}

	protected PersonWriteTO buildSignup() {

		PersonWriteTO seeker = new PersonWriteTO();
		seeker.setAddress(UUID.randomUUID().toString());
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

	protected Contact buildContact() {

		Contact contact = new Contact();
		contact.setSubject(UUID.randomUUID().toString());
		contact.setStatus(ContactStatus.UNREAD);
		contact.setDescription(UUID.randomUUID().toString());

		return contact;
	}

	protected SigninTO buildSignin() {

		SigninTO seeker = new SigninTO();
		seeker.setEmail("123456@email.com");
		seeker.setPassword("123456789");

		return seeker;
	}

	protected FileTO buildFileTO() {
		FileTO file = new FileTO();
		file.setId(UUID.randomUUID());
		file.setFilename(UUID.randomUUID().toString());
		file.setRemoteName(UUID.randomUUID().toString());
		file.setRemoteId(UUID.randomUUID().toString());
		file.setFilesize(1L);
		file.setPath(UUID.randomUUID().toString());
		file.setFileLink(UUID.randomUUID().toString());
		return file;
	}

	protected Category buildCategory(File file) {
		Category cat = new Category();
		cat.setName(UUID.randomUUID().toString());
		cat.setCover(file);
		return cat;
	}

	protected CategoryWriteTO buildCategoryWriteTO() {
		CategoryWriteTO cat = new CategoryWriteTO();
		cat.setName(UUID.randomUUID().toString());
		return cat;
	}

	protected File buildFile() {
		File file = new File();
		file.setName(UUID.randomUUID().toString());
		file.setFileLink(UUID.randomUUID().toString());
		return file;
	}

	protected Announcement buildAnnouncement(Helper creator, Category category) {

		Announcement announcement = new Announcement();
		announcement.setTitle(UUID.randomUUID().toString());
		announcement.setDescription(UUID.randomUUID().toString());
		announcement.setHelper(creator);
		announcement.setCategory(category);
		announcement.setStatus(AnnouncementStatus.PROCESSED);

		return announcement;
	}

	protected AnnouncementWriteTO buildAnnouncementWriteTO(UUID creatorId, UUID categoryId) {

		AnnouncementWriteTO announcement = new AnnouncementWriteTO();
		announcement.setTitle(UUID.randomUUID().toString());
		announcement.setDescription(UUID.randomUUID().toString());
		announcement.setCreatorStatus(RoleType.HELPER);
		announcement.setCreatorId(creatorId);
		announcement.setCategoryId(categoryId);

		return announcement;
	}

	protected Applicant buildApplicant(Seeker creator, Announcement announcement) {

		Applicant applicant = new Applicant();
		applicant.setDetails(UUID.randomUUID().toString());
		applicant.setAnnouncement(announcement);
		applicant.setSeeker(creator);
		applicant.setStatus(ContactStatus.UNREAD);

		return applicant;
	}

	protected Applicant buildApplicant(Announcement announcement) {

		Applicant applicant = new Applicant();
		applicant.setDetails(UUID.randomUUID().toString());
		applicant.setAnnouncement(announcement);
		applicant.setEmail(UUID.randomUUID().toString());
		applicant.setStatus(ContactStatus.UNREAD);
		applicant.setPhone(UUID.randomUUID().toString());
		applicant.setName(UUID.randomUUID().toString());
		return applicant;
	}

	protected ApplicantReadTO buildApplicantReadTO(UUID announcementId) {

		ApplicantReadTO applicant = new ApplicantReadTO();
		applicant.setDetails(UUID.randomUUID().toString());
		applicant.setAnnouncementId(announcementId);
		applicant.setStatus(ContactStatus.UNREAD);
		applicant.setPhone(UUID.randomUUID().toString());
		applicant.setName(UUID.randomUUID().toString());
		applicant.setEmail(UUID.randomUUID().toString());

		return applicant;
	}

	protected ApplicantReadTO buildApplicantReadTO(UUID seekerId, UUID announcementId) {

		ApplicantReadTO applicant = new ApplicantReadTO();
		applicant.setDetails(UUID.randomUUID().toString());
		applicant.setAnnouncementId(announcementId);
		applicant.setCreatorId(seekerId);
		applicant.setStatus(ContactStatus.UNREAD);

		return applicant;
	}
	
	protected Admin buildAdmin(User user) {

		Admin admin = new Admin();
		admin.setAddress(UUID.randomUUID().toString());
		admin.setDob(LocalDate.now());
		admin.setEmail(UUID.randomUUID().toString() + "@email.com");
		admin.setFirstname(UUID.randomUUID().toString());
		admin.setLastname(UUID.randomUUID().toString());
		admin.setGender(Gender.M);
		admin.setPhone("+0012334234543");
		admin.setUser(user);

		return admin;
	}

}
