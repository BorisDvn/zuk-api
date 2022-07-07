package com.thb.zukapi.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thb.zukapi.models.File;

@Repository
public interface FileRepository extends JpaRepository<File, UUID> {

	Optional<File> findByName(String name);

}
