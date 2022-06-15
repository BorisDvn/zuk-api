package com.thb.zukapi.controller;

import com.thb.zukapi.models.Helper;
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

    @Operation(summary = "Get All Helper")
    @ApiResponse(responseCode = "200", description = "Found all Helper",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Helper.class))})
    @GetMapping("")
    public List<Helper> getAllHelfer(@RequestParam(defaultValue = "0") Integer pageNo,
                                     @RequestParam(defaultValue = "10") Integer pageSize,
                                     @RequestParam(defaultValue = "nachname") String sortBy) {
        return helferService.getAll(pageNo, pageSize, sortBy);
    }

    @Operation(summary = "Get a Helper by its id")
    @ApiResponse(responseCode = "200", description = "Found the Helper",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Helper.class))})
    @GetMapping("/{id}")
    public Helper getHelferById(@Parameter(name = "HelferId", description = "ID of the Helfer_obj") @PathVariable UUID id) {
        return helferService.getHelfer(id);
    }

    @Operation(summary = "Add One Helper")
    @ApiResponse(responseCode = "200", description = "Helper added",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Helper.class))})
    @PostMapping("")
    public Helper addHelfer(
            @Parameter(name = "Helper", description = "Helfer_obj to add") @RequestBody Helper helper) {
        return helferService.addHelfer(helper);
    }

    @Operation(summary = "Update Helper")
    @ApiResponse(responseCode = "200", description = "News Helper",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Helper.class))})
    @PutMapping("")
    public Helper updateHelfer(@Parameter(name = "Helper", description = "Helfer_obj to update") @RequestBody Helper helper) {
        return helferService.updateHelfer(helper);
    }

    @Operation(summary = "Delete a Helper by its id")
    @ApiResponse(responseCode = "200", description = "News Helper",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Helper.class))})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHelfer(@Parameter(name = "HelferId", description = "Id of the Helper to delete") @PathVariable UUID id) {
        return helferService.deleteHelferById(id);
    }
}
