package com.thb.zukapi.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thb.zukapi.models.Announcement;
import com.thb.zukapi.models.AnnouncementStype;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, UUID> {

	List<Announcement> findAnnouncementByCategory_Name(String name);
	
	List<Announcement> findByEmail(String email);
	
	List<Announcement> findByType(AnnouncementStype type);

}
