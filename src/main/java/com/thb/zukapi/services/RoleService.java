package com.thb.zukapi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.thb.zukapi.models.Role;
import com.thb.zukapi.models.RoleType;
import com.thb.zukapi.repositories.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	public Role getRole(String name) {
		Role role_ = roleRepository.findByName(RoleType.valueOf(name))
				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
		return role_;
	}

}
