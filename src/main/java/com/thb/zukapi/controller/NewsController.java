package com.thb.zukapi.controller;

import com.thb.zukapi.models.Announcement;
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

    @Operation(summary = "Get All Announcement")
    @ApiResponse(responseCode = "200", description = "Found all Announcement",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Announcement.class))})
    @GetMapping("")
    public List<Announcement> getAllNews(@RequestParam(defaultValue = "0") Integer pageNo,
                                         @RequestParam(defaultValue = "10") Integer pageSize,
                                         @RequestParam(defaultValue = "title") String sortBy) {
        return newsService.getAll(pageNo, pageSize, sortBy);
    }

    @Operation(summary = "Get a Announcement by its id")
    @ApiResponse(responseCode = "200", description = "Found the Announcement",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Announcement.class))})
    @GetMapping("/{id}")
    public Announcement getNewsById(@Parameter(name = "NewsId", description = "ID of the News_obj") @PathVariable UUID id) {
        return newsService.getNews(id);
    }

    @Operation(summary = "Add One Announcement")
    @ApiResponse(responseCode = "200", description = "Announcement added",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Announcement.class))})
    @PostMapping("")
    public Announcement addNews(
            @Parameter(name = "Announcement", description = "News_obj to add") @RequestBody Announcement announcement) {
        return newsService.addNews(announcement);
    }

    @Operation(summary = "Update Announcement")
    @ApiResponse(responseCode = "200", description = "Announcement updated",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Announcement.class))})
    @PutMapping("")
    public Announcement updateNews(@Parameter(name = "Announcement", description = "News_obj to update") @RequestBody Announcement announcement) {
        return newsService.updateNews(announcement);
    }

    @Operation(summary = "Delete a Announcement by its id")
    @ApiResponse(responseCode = "200", description = "Announcement deleted",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Announcement.class))})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteNews(@Parameter(name = "NewsId", description = "Id of the Announcement to delete") @PathVariable UUID id) {
        return newsService.deleteNewsById(id);
    }
}
