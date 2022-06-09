package com.thb.zukapi.controller;

import com.thb.zukapi.models.Helfer;
import com.thb.zukapi.services.HelferService;
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
@RequestMapping("zuk-api/v1/helfers")
@CrossOrigin(origins = "*")
public class HelferController {
    @Autowired
    protected HelferService helferService;

    @Operation(summary = "Get All Helfer")
    @ApiResponse(responseCode = "200", description = "Found all Helfer",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Helfer.class))})
    @GetMapping("")
    public List<Helfer> getAllHelfer(@RequestParam(defaultValue = "0") Integer pageNo,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           @RequestParam(defaultValue = "nachname") String sortBy) {
        return helferService.getAll(pageNo, pageSize, sortBy);
    }

    @Operation(summary = "Get a Helfer by its id")
    @ApiResponse(responseCode = "200", description = "Found the Helfer",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Helfer.class))})
    @GetMapping("/{id}")
    public Helfer getHelferById(@Parameter(name = "HelferId", description = "ID of the Helfer_obj") @PathVariable UUID id) {
        return helferService.getHelfer(id);
    }

    @Operation(summary = "Add One Helfer")
    @ApiResponse(responseCode = "200", description = "Helfer added",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Helfer.class))})
    @PostMapping("")
    public Helfer addHelfer(
            @Parameter(name = "Helfer", description = "Helfer_obj to add") @RequestBody Helfer helfer) {
        return helferService.addHelfer(helfer);
    }

    @Operation(summary = "Update Helfer")
    @ApiResponse(responseCode = "200", description = "News Helfer",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Helfer.class))})
    @PutMapping("")
    public Helfer updateHelfer(@Parameter(name = "Helfer", description = "Helfer_obj to update") @RequestBody Helfer helfer) {
        return helferService.updateHelfer(helfer);
    }

    @Operation(summary = "Delete a Helfer by its id")
    @ApiResponse(responseCode = "200", description = "News Helfer",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Helfer.class))})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHelfer(@Parameter(name = "HelferId", description = "Id of the Helfer to delete") @PathVariable UUID id) {
        return helferService.deleteHelferById(id);
    }
}
