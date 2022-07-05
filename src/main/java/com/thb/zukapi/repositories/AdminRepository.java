package com.thb.zukapi.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thb.zukapi.models.Admin;

@Repository
public interface AdminRepository extends JpaRepository<Admin, UUID> {
	
	Optional<Admin> findByEmail(String email);
}
