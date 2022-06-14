package com.thb.zukapi.repositories;

import com.thb.zukapi.models.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NewsRepository extends JpaRepository<Announcement, UUID> {
}
