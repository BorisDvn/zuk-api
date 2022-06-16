package com.thb.zukapi.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.thb.zukapi.models.Helper;
import com.thb.zukapi.services.HelperService;
import com.thb.zukapi.transfertobjects.user.SignupTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("zuk-api/v1/helper")
@CrossOrigin(origins = "*")
public class HelperController {
    @Autowired
    protected HelperService helperService;

    @Operation(summary = "Get All Helper")
    @ApiResponse(responseCode = "200", description = "Found all Helper",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Helper.class))})
    @GetMapping("")
    public List<Helper> getAllHelper(@RequestParam(defaultValue = "0") Integer pageNo,
                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                     @RequestParam(defaultValue = "nachname") String sortBy) {
        return helperService.getAll(pageNo, pageSize, sortBy);
    }

    @Operation(summary = "Get a Helper by its id")
    @ApiResponse(responseCode = "200", description = "Found the Helper",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Helper.class))})
    @GetMapping("/{id}")
    public Helper getHelperById(@Parameter(name = "HelperId", description = "ID of the Helper_obj") @PathVariable UUID id) {
        return helperService.getHelper(id);
    }

    @Operation(summary = "Add One Helper")
    @ApiResponse(responseCode = "200", description = "Helper added",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Helper.class))})
    @PostMapping("")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public Helper addHelper(
            @Parameter(name = "Helper", description = "Helper_obj to add") @RequestBody SignupTO helper) {
        return helperService.addHelper(helper);
    }

    @Operation(summary = "Update Helper")
    @ApiResponse(responseCode = "200", description = "News Helper",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Helper.class))})
    @PutMapping("")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('HELPER')")
    public Helper updateHelfer(@Parameter(name = "Helper", description = "Helfer_obj to update") @RequestBody Helper helper) {
        return helperService.updateHelper(helper);
    }

    @Operation(summary = "Delete a Helper by its id")
    @ApiResponse(responseCode = "200", description = "News Helper",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Helper.class))})
    @DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteHelfer(@Parameter(name = "HelferId", description = "Id of the Helper to delete") @PathVariable UUID id) {
        return helperService.deleteHelperById(id);
    }
}
