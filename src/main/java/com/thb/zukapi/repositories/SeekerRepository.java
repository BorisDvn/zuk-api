package com.thb.zukapi.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thb.zukapi.models.Seeker;

@Repository
public interface SeekerRepository extends JpaRepository<Seeker, UUID> {
	
	Optional<Seeker> findByEmail(String email);

}