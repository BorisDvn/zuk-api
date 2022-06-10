package com.thb.zukapi.controller;

import com.thb.zukapi.models.News;
import com.thb.zukapi.services.NewsService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("zuk-api/v1/information")
@CrossOrigin(origins = "*")
public class NewsController {
    @Autowired
    protected NewsService newsService;

    @ApiOperation("Get All News")
    @GetMapping("")
    public List<News> getAllNews(@RequestParam(defaultValue = "0") Integer pageNo,
                                        @RequestParam(defaultValue = "10") Integer pageSize,
                                        @RequestParam(defaultValue = "name") String sortBy) {
        return newsService.getAll(pageNo, pageSize, sortBy);
    }

    @ApiOperation("Get One News")
    @GetMapping("/{id}")
    public News getNewsById(@ApiParam(name = "NewsId", value = "ID of the new object to be saved") @PathVariable UUID id) {
        return newsService.getNews(id);
    }

    @ApiOperation("Add a new News")
    @PostMapping("")
    public News addNews(
            @ApiParam(name = "News", value = "News to be added") @RequestBody News news) {
        return newsService.addNews(news);
    }

    @ApiOperation("Update a News")
    @PutMapping("")
    public News updateNews(@ApiParam(name = "News", value = "News to be updated") @RequestBody News news) {
        return newsService.updateNews(news);
    }

    @ApiOperation("Delete News")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNews(@ApiParam(name = "newsId", value = "Id of the News") @PathVariable UUID id) {
        return newsService.deleteNews(id);
    }
}
