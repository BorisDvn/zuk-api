package com.thb.zukapi.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thb.zukapi.models.Role;
import com.thb.zukapi.models.RoleType;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID>{
	
	Optional<Role> findByName(RoleType name);
	
	Boolean existsByName(RoleType name);

}
