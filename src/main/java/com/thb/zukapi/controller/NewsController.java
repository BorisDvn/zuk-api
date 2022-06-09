package com.thb.zukapi.controller;

import com.thb.zukapi.models.News;
import com.thb.zukapi.services.NewsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("zuk-api/v1/news")
@CrossOrigin(origins = "*")
public class NewsController {
    @Autowired
    protected NewsService newsService;

    @Operation(summary = "Get All News")
    @ApiResponse(responseCode = "200", description = "Found all News",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = News.class))})
    @GetMapping("")
    public List<News> getAllNews(@RequestParam(defaultValue = "0") Integer pageNo,
                                 @RequestParam(defaultValue = "10") Integer pageSize,
                                 @RequestParam(defaultValue = "title") String sortBy) {
        return newsService.getAll(pageNo, pageSize, sortBy);
    }

    @Operation(summary = "Get a News by its id")
    @ApiResponse(responseCode = "200", description = "Found the News",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = News.class))})
    @GetMapping("/{id}")
    public News getNewsById(@Parameter(name = "NewsId", description = "ID of the News_obj") @PathVariable UUID id) {
        return newsService.getNews(id);
    }

    @Operation(summary = "Add One News")
    @ApiResponse(responseCode = "200", description = "News added",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = News.class))})
    @PostMapping("")
    public News addNews(
            @Parameter(name = "News", description = "News_obj to add") @RequestBody News news) {
        return newsService.addNews(news);
    }

    @Operation(summary = "Update News")
    @ApiResponse(responseCode = "200", description = "News updated",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = News.class))})
    @PutMapping("")
    public News updateNews(@Parameter(name = "News", description = "News_obj to update") @RequestBody News news) {
        return newsService.updateNews(news);
    }

    @Operation(summary = "Delete a News by its id")
    @ApiResponse(responseCode = "200", description = "News deleted",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = News.class))})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNews(@Parameter(name = "NewsId", description = "Id of the News to delete") @PathVariable UUID id) {
        return newsService.deleteNewsById(id);
    }
}