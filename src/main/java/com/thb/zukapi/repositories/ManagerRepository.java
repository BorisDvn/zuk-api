package com.thb.zukapi.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thb.zukapi.models.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, UUID> {
	
	Optional<Manager> findByEmail(String email);

}