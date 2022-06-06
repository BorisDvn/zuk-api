package com.thb.zukapi.services;

import com.thb.zukapi.exception.ApiRequestException;
import com.thb.zukapi.models.Fluechtling;
import com.thb.zukapi.models.News;
import com.thb.zukapi.repositories.FluechtlingRepository;
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

import java.util.List;
import java.util.UUID;

@Service
public class FluechtlingService {
    private final Logger logger = LoggerFactory.getLogger(FluechtlingService.class);

    @Autowired
    private FluechtlingRepository fluechtlingRepository;

    public Fluechtling getNews(UUID id) {
        return fluechtlingRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Cannot find Fluechtling with id: " + id));
    }

    /*public List<Fluechtling> getAll(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<News> pagedResult = newsRepository.findAll(paging);

        return pagedResult.getContent();
    }

    public Fluechtling addNews(News news) {

        News newNews = new News();

        newNews.setTitle(news.getTitle());
        newNews.setNachricht(news.getNachricht());
        newNews.setDatum(news.getDatum());
        newNews.setBild(news.getBild());

        logger.info("News successfully added");
        return newsRepository.save(newNews);
    }

    public Fluechtling updateFluechtling(Fluechtling fluechtling) {

        News newsToUpdate = getNews(news.getId());

        if (news.getTitle() != null)
            news.setTitle(news.getTitle());
        if (news.getNachricht() != null)
            news.setNachricht(news.getNachricht());
        if (news.getDatum() != null)
            news.setDatum(news.getDatum());

        logger.info("News successfully updated");
        return newsRepository.save(newsToUpdate);
    }

    public ResponseEntity<?> deleteNewsById(UUID id) {
        News newsToDelete = getNews(id);

        newsRepository.deleteById(newsToDelete.getId());

        logger.info("News successfully deleted");
        return new ResponseEntity<>("News successfully deleted", HttpStatus.OK);
    }*/
}
