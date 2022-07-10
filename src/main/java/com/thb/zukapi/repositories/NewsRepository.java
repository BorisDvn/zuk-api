package com.thb.zukapi.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thb.zukapi.models.News;

@Repository
public interface NewsRepository extends JpaRepository<News, UUID> {
	
	Optional<News> findByTitle(String title);
	
}
