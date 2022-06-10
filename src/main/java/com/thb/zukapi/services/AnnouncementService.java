package com.thb.zukapi.services;

import com.thb.zukapi.exception.ApiRequestException;
import com.thb.zukapi.models.Announcement;
import com.thb.zukapi.repositories.AnnouncementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AnnouncementService {
    //todo: log

    @Autowired
    private AnnouncementRepository announcementRepository;

    public Announcement getAnnouncement(UUID id) {
        return announcementRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Cannot find Announcement with id: " + id));
    }

    public List<Announcement> getAll(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Announcement> pagedResult = announcementRepository.findAll(paging);

        return pagedResult.getContent();
    }

    public Announcement addAnnouncement(Announcement announcement) {

        Announcement newAnnouncement = new Announcement();
        newAnnouncement.setType(announcement.getType());
        // todo image as link?
        newAnnouncement.setDescription(announcement.getDescription());
        newAnnouncement.setTitle(announcement.getTitle());
        newAnnouncement.setImages(announcement.getImages());

        // todo FK
        return announcementRepository.save(newAnnouncement);
    }

    public Announcement updateAnnouncement(Announcement announcement) {

        Announcement announcementToUpdate = getAnnouncement(announcement.getId());

        if (!announcement.getTitle().isBlank()) announcement.setTitle(announcement.getTitle());
        if (announcement.getType() != null && !announcement.getType().toString().isBlank())
            announcement.setType(announcement.getType());
        if (announcement.getImages() != null)
            announcement.setImages(announcement.getImages());
        if (!announcement.getDescription().isBlank())
            announcement.setDescription(announcement.getDescription());

        // todo FK

        return announcementRepository.save(announcementToUpdate);
    }

    public ResponseEntity<?> deleteAnnouncement(UUID id) {
        Announcement toDelete = getAnnouncement(id);

        announcementRepository.deleteById(toDelete.getId());
        // log.info("successfully deleted");
        return new ResponseEntity<>(true, HttpStatus.OK);
    }
}
