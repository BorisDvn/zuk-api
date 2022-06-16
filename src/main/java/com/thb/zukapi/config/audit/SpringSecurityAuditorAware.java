package com.thb.zukapi.config.audit;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;

public class SpringSecurityAuditorAware implements AuditorAware<String> {

	@Override
	public Optional<String> getCurrentAuditor() {

		// TODO Set the Auditor name: current authenticated user
		return Optional.ofNullable("Kindson").filter(s -> !s.isEmpty());
	}

}