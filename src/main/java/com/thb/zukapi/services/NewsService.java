package com.thb.zukapi.services;

import com.thb.zukapi.exception.ApiRequestException;
import com.thb.zukapi.models.News;
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

    public News getNews(UUID id) {
        return newsRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Cannot find News with id: " + id));
    }

    public List<News> getAll(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<News> pagedResult = newsRepository.findAll(paging);

        return pagedResult.getContent();
    }

    public News addNews(News news) {

        News newNews = new News();

        newNews.setTitle(news.getTitle());
        newNews.setImages(news.getImages());
        newNews.setDescription(news.getDescription());
        newNews.setPublicationDate(LocalDateTime.now());

        return newsRepository.save(newNews);
    }

    public News updateNews(News news) {

        News newsToUpdate = getNews(news.getId());

        if (news.getTitle() != null)
            newsToUpdate.setTitle(news.getTitle());
        if (news.getImages() != null)
            newsToUpdate.setImages(news.getImages());
        if (news.getDescription() != null)
            newsToUpdate.setDescription(news.getDescription());
        if (news.getPublicationDate() != null) // TODO: update date? like last update?
            newsToUpdate.setPublicationDate(news.getPublicationDate());

        return newsRepository.save(newsToUpdate);
    }

    public ResponseEntity<String> deleteNewsById(UUID id) {
        News newsToDelete = getNews(id);

        newsRepository.deleteById(newsToDelete.getId());

        logger.info("News successfully deleted");
        return new ResponseEntity<>("News successfully deleted", HttpStatus.OK);
    }
}
