package com.thb.zukapi.services;

import com.thb.zukapi.exception.ApiRequestException;
import com.thb.zukapi.models.Announcement;
import com.thb.zukapi.repositories.NewsRepository;
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
public class NewsService {

    private final Logger logger = LoggerFactory.getLogger(NewsService.class);

    @Autowired
    private NewsRepository newsRepository;

    public Announcement getNews(UUID id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Cannot find Announcement with id: " + id));
    }

    public List<Announcement> getAll(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Announcement> pagedResult = newsRepository.findAll(paging);

        return pagedResult.getContent();
    }

    public Announcement addNews(Announcement announcement) {

        Announcement newAnnouncement = new Announcement();

        newAnnouncement.setTitle(announcement.getTitle());
        newAnnouncement.setDescription(announcement.getDescription());
        newAnnouncement.setDate(LocalDateTime.now());
        newAnnouncement.setBild(announcement.getBild());

        return newsRepository.save(newAnnouncement);
    }

    public Announcement updateNews(Announcement announcement) {

        Announcement announcementToUpdate = getNews(announcement.getId());

        if (announcement.getTitle() != null)
            announcement.setTitle(announcement.getTitle());
        if (announcement.getDescription() != null)
            announcement.setDescription(announcement.getDescription());
        if (announcement.getDate() != null) // TODO: update date? like last update?
            announcement.setDate(announcement.getDate());

        return newsRepository.save(announcementToUpdate);
    }

    public ResponseEntity<String> deleteNewsById(UUID id) {
        Announcement announcementToDelete = getNews(id);

        newsRepository.deleteById(announcementToDelete.getId());

        logger.info("Announcement successfully deleted");
        return new ResponseEntity<>("Announcement successfully deleted", HttpStatus.OK);
    }
}
