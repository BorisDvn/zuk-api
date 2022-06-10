package com.thb.zukapi.services;

import com.thb.zukapi.exception.ApiRequestException;
import com.thb.zukapi.models.News;
import com.thb.zukapi.repositories.NewsRepository;
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
public class NewsService {

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
        newNews.setDescription(news.getDescription());
        newNews.setTitle(news.getTitle());

        return newsRepository.save(newNews);
    }

    public News updateNews(News news) {

        News newsToUpdate = getNews(news.getId());

        if (!news.getTitle().isBlank()) news.setTitle(news.getTitle());
        if (!news.getDescription().isBlank()) news.setDescription(news.getDescription());
        if (news.getImage() != null) news.setImage(news.getImage());
        return newsRepository.save(newsToUpdate);
    }

    public ResponseEntity<?> deleteNews(UUID id) {
        News newsToDelete = getNews(id);

        newsRepository.deleteById(newsToDelete.getId());
        // log.info("successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }
}
