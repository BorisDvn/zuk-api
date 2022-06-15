package com.thb.zukapi.controller;

import com.thb.zukapi.models.Seeker;
import com.thb.zukapi.services.SeekerService;
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
@RequestMapping("zuk-api/v1/seeker")
@CrossOrigin(origins = "*")
public class SeekerController {
    @Autowired
    protected SeekerService seekerService;

    @Operation(summary = "Get All Seeker")
    @ApiResponse(responseCode = "200", description = "Found all Seeker",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Seeker.class))})
    @GetMapping("")
    public List<Seeker> getAllSeeker(@RequestParam(defaultValue = "0") Integer pageNo,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          @RequestParam(defaultValue = "nachname") String sortBy) {
        return seekerService.getAll(pageNo, pageSize, sortBy);
    }

    @Operation(summary = "Get a Seeker by its id")
    @ApiResponse(responseCode = "200", description = "Found the Seeker",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Seeker.class))})
    @GetMapping("/{id}")
    public Seeker getSeekerById(@Parameter(name = "SeekerId", description = "ID of the Seeker_obj") @PathVariable UUID id) {
        return seekerService.getSeeker(id);
    }

    @Operation(summary = "Add One Seeker")
    @ApiResponse(responseCode = "200", description = "Seeker added",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Seeker.class))})
    @PostMapping("")
    public Seeker addSeeker(
            @Parameter(name = "Seeker", description = "Seeker_obj to add") @RequestBody Seeker seeker) {
        return seekerService.addSeeker(seeker);
    }

    @Operation(summary = "Update Seeker")
    @ApiResponse(responseCode = "200", description = "News Seeker",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Seeker.class))})
    @PutMapping("")
    public Seeker updateSeeker(@Parameter(name = "Seeker", description = "Seeker_obj to update") @RequestBody Seeker seeker) {
        return seekerService.updateSeeker(seeker);
    }

    @Operation(summary = "Delete a Seeker by its id")
    @ApiResponse(responseCode = "200", description = "News Seeker",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Seeker.class))})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSeeker(@Parameter(name = "SeekerId", description = "Id of the Seeker to delete") @PathVariable UUID id) {
        return seekerService.deleteSeekerById(id);
    }
}
