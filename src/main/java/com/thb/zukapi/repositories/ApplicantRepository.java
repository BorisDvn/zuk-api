package com.thb.zukapi.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thb.zukapi.models.Applicant;

@Repository
public interface ApplicantRepository extends JpaRepository<Applicant, UUID> {
	
	List<Applicant> findByAnnouncement_Id(UUID id);
	
	List<Applicant> findBySeeker_Email(String email);
	
	Optional<Applicant> findByEmail(String email);
	
}
