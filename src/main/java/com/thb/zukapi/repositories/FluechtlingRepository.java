package com.thb.zukapi.repositories;

import com.thb.zukapi.models.Fluechtling;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface FluechtlingRepository extends JpaRepository<Fluechtling, UUID> {
}
