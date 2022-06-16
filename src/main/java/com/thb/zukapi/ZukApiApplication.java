package com.thb.zukapi;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.thb.zukapi.config.audit.SpringSecurityAuditorAware;
import com.thb.zukapi.models.Role;
import com.thb.zukapi.models.RoleType;
import com.thb.zukapi.repositories.RoleRepository;

@EnableJpaAuditing(auditorAwareRef="auditorAware")
@SpringBootApplication
public class ZukApiApplication implements CommandLineRunner{
	
	@Autowired
	private RoleRepository roleRepository;
	
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
		Role role1 = new Role();
		role1.setId(UUID.randomUUID());
		role1.setName(RoleType.ADMIN);
		roleRepository.save(role1);

		Role role2 = new Role();
		role2.setId(UUID.randomUUID());
		role2.setName(RoleType.HELPER);
		roleRepository.save(role2);

		Role role3 = new Role();
		role3.setId(UUID.randomUUID());
		role3.setName(RoleType.MANAGER);
		roleRepository.save(role3);
		
		Role role4 = new Role();
		role4.setId(UUID.randomUUID());
		role4.setName(RoleType.SEEKER);
		roleRepository.save(role4);
	}

}
