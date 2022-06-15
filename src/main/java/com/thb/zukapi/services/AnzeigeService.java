package com.thb.zukapi.services;

import com.thb.zukapi.exception.ApiRequestException;
import com.thb.zukapi.models.Announcement;
import com.thb.zukapi.models.ErstellerStatus;
import com.thb.zukapi.repositories.AnzeigeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AnzeigeService {
    private final Logger logger = LoggerFactory.getLogger(AnzeigeService.class);

    @Autowired
    private AnzeigeRepository anzeigeRepository;

    public Announcement getAnzeige(UUID id) {
        return anzeigeRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Cannot find Announcement with id: " + id));
    }

    public List<Announcement> getAll(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Announcement> pagedResult = anzeigeRepository.findAll(paging);

        return pagedResult.getContent();
    }

    public Announcement addAnzeige(Announcement announcement) {

        Announcement newAnnouncement = new Announcement();

        if (announcement.getHelper() != null) {
            newAnnouncement.setType(ErstellerStatus.HELFER);
        } else {
            newAnnouncement.setType(ErstellerStatus.FLUECHTLING);
        }

        newAnnouncement.setDatum(LocalDateTime.now());
        // todo bilder als link?
        newAnnouncement.setDescription(announcement.getDescription());
        newAnnouncement.setStatus(announcement.getStatus());

        // todo FK
        return anzeigeRepository.save(newAnnouncement);
    }

    public Announcement updateAnzeige(Announcement announcement) {

        Announcement announcementToUpdate = getAnzeige(announcement.getId());

        if (announcement.getType() != null)
            announcement.setType(announcement.getType());
        if (announcement.getDatum() != null) //todo bilder
            announcement.setDatum(announcement.getDatum());
        if (announcement.getBilds() != null)
            announcement.setBilds(announcement.getBilds());
        if (announcement.getDescription() != null)
            announcement.setDescription(announcement.getDescription());

        // todo FK

        return anzeigeRepository.save(announcementToUpdate);
    }

    public ResponseEntity<String> deleteAnzeigeById(UUID id) {
        Announcement announcementToDelete = getAnzeige(id);

        anzeigeRepository.deleteById(announcementToDelete.getId());
        // log.info("successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }


}
