package com.thb.zukapi.controller;

import com.thb.zukapi.models.Fluechtling;
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

    @Operation(summary = "Get All Fluechtling")
    @ApiResponse(responseCode = "200", description = "Found all Fluechtling",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Fluechtling.class))})
    @GetMapping("")
    public List<Fluechtling> getAllFluechtling(@RequestParam(defaultValue = "0") Integer pageNo,
                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                               @RequestParam(defaultValue = "nachname") String sortBy) {
        return fluechtlingService.getAll(pageNo, pageSize, sortBy);
    }

    @Operation(summary = "Get a Fluechtling by its id")
    @ApiResponse(responseCode = "200", description = "Found the Fluechtling",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Fluechtling.class))})
    @GetMapping("/{id}")
    public Fluechtling getFluechtlingById(@Parameter(name = "FluechtlingId", description = "ID of the Fluechtling_obj") @PathVariable UUID id) {
        return fluechtlingService.getFluechtling(id);
    }

    @Operation(summary = "Add One Fluechtling")
    @ApiResponse(responseCode = "200", description = "Fluechtling added",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Fluechtling.class))})
    @PostMapping("")
    public Fluechtling addFluechtling(
            @Parameter(name = "Fluechtling", description = "Fluechtling_obj to add") @RequestBody Fluechtling fluechtling) {
        return fluechtlingService.addFluechtling(fluechtling);
    }

    @Operation(summary = "Update Fluechtling")
    @ApiResponse(responseCode = "200", description = "Announcement Fluechtling",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Fluechtling.class))})
    @PutMapping("")
    public Fluechtling updateFluechtling(@Parameter(name = "Fluechtling", description = "Fluechtling_obj to update") @RequestBody Fluechtling fluechtling) {
        return fluechtlingService.updateFluechtling(fluechtling);
    }

    @Operation(summary = "Delete a Fluechtling by its id")
    @ApiResponse(responseCode = "200", description = "Announcement Fluechtling",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Fluechtling.class))})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFluechtling(@Parameter(name = "FluechtlingId", description = "Id of the Fluechtling to delete") @PathVariable UUID id) {
        return fluechtlingService.deleteFluechtlingById(id);
    }
}
