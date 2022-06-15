package com.thb.zukapi.controller;

import com.thb.zukapi.models.Seeker;
import com.thb.zukapi.services.FluechtlingService;
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
@RequestMapping("zuk-api/v1/fluechtlingen")
@CrossOrigin(origins = "*")
public class FluechtlingController {
    @Autowired
    protected FluechtlingService fluechtlingService;

    @Operation(summary = "Get All Seeker")
    @ApiResponse(responseCode = "200", description = "Found all Seeker",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Seeker.class))})
    @GetMapping("")
    public List<Seeker> getAllFluechtling(@RequestParam(defaultValue = "0") Integer pageNo,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          @RequestParam(defaultValue = "nachname") String sortBy) {
        return fluechtlingService.getAll(pageNo, pageSize, sortBy);
    }

    @Operation(summary = "Get a Seeker by its id")
    @ApiResponse(responseCode = "200", description = "Found the Seeker",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Seeker.class))})
    @GetMapping("/{id}")
    public Seeker getFluechtlingById(@Parameter(name = "FluechtlingId", description = "ID of the Fluechtling_obj") @PathVariable UUID id) {
        return fluechtlingService.getFluechtling(id);
    }

    @Operation(summary = "Add One Seeker")
    @ApiResponse(responseCode = "200", description = "Seeker added",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Seeker.class))})
    @PostMapping("")
    public Seeker addFluechtling(
            @Parameter(name = "Seeker", description = "Fluechtling_obj to add") @RequestBody Seeker seeker) {
        return fluechtlingService.addFluechtling(seeker);
    }

    @Operation(summary = "Update Seeker")
    @ApiResponse(responseCode = "200", description = "News Seeker",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Seeker.class))})
    @PutMapping("")
    public Seeker updateFluechtling(@Parameter(name = "Seeker", description = "Fluechtling_obj to update") @RequestBody Seeker seeker) {
        return fluechtlingService.updateFluechtling(seeker);
    }

    @Operation(summary = "Delete a Seeker by its id")
    @ApiResponse(responseCode = "200", description = "News Seeker",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Seeker.class))})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFluechtling(@Parameter(name = "FluechtlingId", description = "Id of the Seeker to delete") @PathVariable UUID id) {
        return fluechtlingService.deleteFluechtlingById(id);
    }
}
