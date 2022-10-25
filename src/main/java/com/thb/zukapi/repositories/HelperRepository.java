package com.thb.zukapi.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thb.zukapi.models.Helper;

@Repository
public interface HelperRepository extends JpaRepository<Helper, UUID> {

	Optional<Helper> findByEmail(String email);

}
