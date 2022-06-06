package com.thb.zukapi.controller;

import com.thb.zukapi.models.Information;
import com.thb.zukapi.services.InformationService;
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
@RequestMapping("zuk-api/v1/information")
@CrossOrigin(origins = "*")
public class InformationController {
    @Autowired
    protected InformationService informationService;

    @Operation(summary = "Get All Information")
    @ApiResponse(responseCode = "200", description = "Found all Information",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Information.class))})
    @GetMapping("")
    public List<Information> getAllInformation(@RequestParam(defaultValue = "0") Integer pageNo,
                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                               @RequestParam(defaultValue = "name") String sortBy) {
        return informationService.getAll(pageNo, pageSize, sortBy);
    }

    @Operation(summary = "Get a Information by its id")
    @ApiResponse(responseCode = "200", description = "Found the Information",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Information.class))})
    @GetMapping("/{id}")
    public Information getInformationById(@Parameter(name = "InformationId", description = "ID of the Information_obj") @PathVariable UUID id) {
        return informationService.getInformation(id);
    }

    @Operation(summary ="Add One Information")
    @ApiResponse(responseCode = "200", description = "Information added",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Information.class))})
    @PostMapping("")
    public Information addInformation(
            @Parameter(name = "Information", description = "Information_obj to add") @RequestBody Information information) {
        return informationService.addInformation(information);
    }

    @Operation(summary ="Update Information")
    @ApiResponse(responseCode = "200", description = "Information updated",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Information.class))})
    @PutMapping("")
    public Information updateInformation(@Parameter(name = "Information", description = "Information_obj to update") @RequestBody Information information) {
        return informationService.updateInformation(information);
    }

    @Operation(summary ="Delete a Information by its id")
    @ApiResponse(responseCode = "200", description = "Information deleted",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Information.class))})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInformation(@Parameter(name = "InformationId", description = "Id of the Information to delete") @PathVariable UUID id) {
        return informationService.deleteInformation(id);
    }
}
