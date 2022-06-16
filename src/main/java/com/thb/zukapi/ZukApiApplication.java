package com.thb.zukapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.thb.zukapi.config.audit.SpringSecurityAuditorAware;

@EnableJpaAuditing(auditorAwareRef="auditorAware")
@SpringBootApplication
public class ZukApiApplication {
	
	// Auditing Config
	@Bean
	public AuditorAware<String> auditorAware() {
		return new SpringSecurityAuditorAware();
	}

	public static void main(String[] args) {
		SpringApplication.run(ZukApiApplication.class, args);
	}

}
