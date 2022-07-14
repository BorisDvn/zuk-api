package com.thb.zukapi;

import java.time.LocalDate;
import java.util.UUID;

import com.thb.zukapi.models.Manager;
import com.thb.zukapi.repositories.ManagerRepository;
import com.thb.zukapi.services.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.thb.zukapi.config.audit.SpringSecurityAuditorAware;
import com.thb.zukapi.dtos.person.PersonWriteTO;
import com.thb.zukapi.models.Gender;
import com.thb.zukapi.models.Role;
import com.thb.zukapi.models.RoleType;
import com.thb.zukapi.repositories.AdminRepository;
import com.thb.zukapi.repositories.RoleRepository;
import com.thb.zukapi.services.AdminService;

@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@SpringBootApplication
public class ZukApiApplication implements CommandLineRunner {

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ManagerRepository managerRepository;

	@Autowired
	private ManagerService managerService;
	@Autowired
	private AdminRepository adminRepo;

	@Autowired
	private AdminService adminService;

	// Auditing Config
	@Bean
	public AuditorAware<String> auditorAware() {
		return new SpringSecurityAuditorAware();
	}

	public static void main(String[] args) {
		SpringApplication.run(ZukApiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		PersonWriteTO admin = new PersonWriteTO();
		admin.setEmail("admin-default@local.com");
		admin.setLastname("12345");
		admin.setFirstname("admin");
		admin.setAddress("addres");
		admin.setPhone("123456789");
		admin.setPassword("admin-default");
		admin.setRole("ADMIN");
		admin.setDob(LocalDate.now());
		admin.setGender(Gender.M);
		if (!adminRepo.findByEmail(admin.getEmail()).isPresent()) {
			adminService.addAdmin(admin);
		}
			

		// Save roles only one type
		Role role1 = new Role();
		role1.setId(UUID.randomUUID());
		role1.setName(RoleType.ADMIN);
		if (!roleRepository.existsByName(role1.getName()))
			roleRepository.save(role1);

		Role role2 = new Role();
		role2.setId(UUID.randomUUID());
		role2.setName(RoleType.HELPER);
		if (!roleRepository.existsByName(role2.getName()))
			roleRepository.save(role2);

		Role role3 = new Role();
		role3.setId(UUID.randomUUID());
		role3.setName(RoleType.MANAGER);
		if (!roleRepository.existsByName(role3.getName()))
			roleRepository.save(role3);

		Role role4 = new Role();
		role4.setId(UUID.randomUUID());
		role4.setName(RoleType.SEEKER);
		if (!roleRepository.existsByName(role4.getName()))
			roleRepository.save(role4);


		//Manager
		PersonWriteTO manager = new PersonWriteTO();
		manager.setEmail("manager-default@local.com");
		manager.setLastname("default");
		manager.setFirstname("manager");
		manager.setAddress("addres");
		manager.setPhone("123456789");
		manager.setPassword("manager-default");
		manager.setRole("MANAGER");
		manager.setDob(LocalDate.now());
		manager.setGender(Gender.F);
		if (!managerRepository.findByEmail(manager.getEmail()).isPresent()) {
			managerService.addManager(manager);
		}

	}

}
