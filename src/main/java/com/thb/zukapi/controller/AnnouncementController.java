package com.thb.zukapi.controller;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.thb.zukapi.dtos.announcements.AnnouncementReadTO;
import com.thb.zukapi.dtos.announcements.AnnouncementWriteTO;
import com.thb.zukapi.models.Announcement;
import com.thb.zukapi.services.AnnouncementService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;


@RestController
@RequestMapping("zuk-api/v1/announcement")
@CrossOrigin(origins = "*")
public class AnnouncementController {
    @Autowired
    protected AnnouncementService announcementService;

    @Operation(summary = "Get All Announcement")
    @ApiResponse(responseCode = "200", description = "Found all Announcement",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = AnnouncementReadTO.class))})
    @GetMapping("")
    public List<AnnouncementReadTO> getAllAnnouncement(@RequestParam(defaultValue = "0") Integer pageNo,
                                                   @RequestParam(defaultValue = "15") Integer pageSize,
                                                   @RequestParam(defaultValue = "name") String sortBy) {
        return announcementService.getAll(pageNo, pageSize, sortBy);
    }

    @Operation(summary = "Get a Announcement by its id")
    @ApiResponse(responseCode = "200", description = "Found the Announcement",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = AnnouncementReadTO.class))})
    @GetMapping("/{id}")
    public AnnouncementReadTO getAnnouncementById(@Parameter(name = "AnnouncementId", description = "ID of the Announcement_obj") @PathVariable UUID id) {
        return announcementService.getAnnouncement(id);
    }

    @Operation(summary = "Add image to Announcement")
    @ApiResponse(responseCode = "200", description = "Image added",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = AnnouncementReadTO.class))})
    @PostMapping("/{announcementId}")
    public AnnouncementReadTO addImageAnnouncement(
    		@Parameter(name = "AnnouncementId", description = "Id of the Announcement") @PathVariable UUID announcementId,
    		@Parameter(name = "file", description = "File to be added") @RequestPart(required = true) MultipartFile file) {
        return announcementService.addImageAnnouncement(announcementId, file);
    }
    
    @Operation(summary = "remove image from Announcement")
    @ApiResponse(responseCode = "200", description = "Image removed",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = AnnouncementReadTO.class))})
    @DeleteMapping("/{announcementId}")
    public AnnouncementReadTO removeImageAnnouncement(
    		@Parameter(name = "AnnouncementId", description = "Id of the Announcement") @PathVariable UUID announcementId,
    		@Parameter(name = "file", description = "File to be removed") @RequestPart(required = true) MultipartFile file) {
        return announcementService.removeImageAnnouncement(announcementId, file);
    }

    @Operation(summary = "Add One Announcement")
    @ApiResponse(responseCode = "200", description = "Announcement added",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = AnnouncementReadTO.class))})
    @PostMapping("")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public AnnouncementReadTO addAnnouncement(
            @Parameter(name = "Announcement", description = "Announcement_obj to add") @RequestPart AnnouncementWriteTO announcement,
            @Parameter(name = "file", description = "File to be removed")  @RequestPart(required = false) List<MultipartFile> files) throws IOException {
        return announcementService.addAnnouncement(announcement, files);
    }

    @Operation(summary = "Update Announcement")
    @ApiResponse(responseCode = "200", description = "Update Announcement",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = AnnouncementReadTO.class))})
    @PutMapping("")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public AnnouncementReadTO updateAnnouncement(@Parameter(name = "Announcement", description = "Announcement_obj to update") @RequestBody AnnouncementWriteTO announcement) {
        return announcementService.updateAnnouncement(announcement);
    }

    @Operation(summary = "Delete a Announcement by its id")
    @ApiResponse(responseCode = "200", description = "Delete Announcement",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Announcement.class))})
    @DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteAnnouncement(@Parameter(name = "AnnouncementId", description = "Id of the Announcement to delete") @PathVariable UUID id) {
        return announcementService.deleteAnnouncementById(id);
    }
}
