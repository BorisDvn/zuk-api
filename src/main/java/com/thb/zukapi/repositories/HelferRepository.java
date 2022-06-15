package com.thb.zukapi.repositories;

import com.thb.zukapi.models.Helper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface HelferRepository extends JpaRepository<Helper, UUID> {
}
