package com.thb.zukapi.controller;

import com.thb.zukapi.dtos.helper.HelperReadListTO;
import com.thb.zukapi.dtos.helper.HelperReadTO;
import com.thb.zukapi.dtos.helper.HelperWriteTO;
import com.thb.zukapi.models.Helper;
import com.thb.zukapi.services.HelperService;
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
@RequestMapping("zuk-api/v1/helper")
@CrossOrigin(origins = "*")
public class HelperController {
    @Autowired
    protected HelperService helperService;

    @Operation(summary = "Get All Helper")
    @ApiResponse(responseCode = "200", description = "Found all Helper",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = HelperReadListTO.class))})
    @GetMapping("")
    public List<HelperReadListTO> getAllHelper(@RequestParam(defaultValue = "0") Integer pageNo,
                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                               @RequestParam(defaultValue = "firstname") String sortBy) {
        return helperService.getAll(pageNo, pageSize, sortBy);
    }

    @Operation(summary = "Get a Helper by its id")
    @ApiResponse(responseCode = "200", description = "Found the Helper",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = HelperReadTO.class))})
    @GetMapping("/{id}")
    public HelperReadTO getHelperById(@Parameter(name = "HelperId", description = "ID of the Helper_obj") @PathVariable UUID id) {
        return helperService.getHelper(id);
    }

    @Operation(summary = "Add One Helper")
    @ApiResponse(responseCode = "200", description = "Helper added",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = HelperWriteTO.class))})
    @PostMapping("")
    public HelperReadTO addHelper(
            @Parameter(name = "Helper", description = "Helper_obj to add") @RequestBody HelperWriteTO helper) {
        return helperService.addHelper(helper);
    }

    @Operation(summary = "Update Helper")
    @ApiResponse(responseCode = "200", description = "News Helper",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = HelperWriteTO.class))})
    @PutMapping("")
    public HelperReadTO updateHelfer(@Parameter(name = "Helper", description = "Helfer_obj to update") @RequestBody HelperWriteTO helper) {
        return helperService.updateHelper(helper);
    }

    @Operation(summary = "Delete a Helper by its id")
    @ApiResponse(responseCode = "200", description = "News Helper",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Helper.class))})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteHelfer(@Parameter(name = "HelferId", description = "Id of the Helper to delete") @PathVariable UUID id) {
        return helperService.deleteHelperById(id);
    }
}
