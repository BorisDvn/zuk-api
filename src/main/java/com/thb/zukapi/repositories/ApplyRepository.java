package com.thb.zukapi.repositories;

import com.thb.zukapi.models.Apply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ApplyRepository extends JpaRepository<Apply, UUID> {
}
