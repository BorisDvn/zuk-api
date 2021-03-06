package com.thb.zukapi.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

import com.thb.zukapi.dtos.manager.ManagerReadListTO;
import com.thb.zukapi.dtos.manager.ManagerReadTO;
import com.thb.zukapi.dtos.person.PersonWriteTO;
import com.thb.zukapi.models.Manager;
import com.thb.zukapi.services.ManagerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("zuk-api/v1/manager")
@CrossOrigin(origins = "*")
public class ManagerController {
    @Autowired
    protected ManagerService managerService;

    @Operation(summary = "Get All Manager")
    @ApiResponse(responseCode = "200", description = "Found all Manager",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ManagerReadListTO.class))})
    @GetMapping("")
    public List<ManagerReadListTO> getAllManager(@RequestParam(defaultValue = "0") Integer pageNo,
                                       @RequestParam(defaultValue = "10") Integer pageSize,
                                       @RequestParam(defaultValue = "firstname") String sortBy) {
        return managerService.getAll(pageNo, pageSize, sortBy);
    }

    @Operation(summary = "Get a Manager by its id")
    @ApiResponse(responseCode = "200", description = "Found the Manager",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ManagerReadTO.class))})
    @GetMapping("/{id}")
    public ManagerReadTO getManagerById(@Parameter(name = "ManagerId", description = "ID of the Manager_obj") @PathVariable UUID id) {
        return managerService.getManager(id);
    }

    @Operation(summary = "Add One Manager")
    @ApiResponse(responseCode = "200", description = "Manager added",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ManagerReadTO.class))})
    @PostMapping("")
    public ManagerReadTO addManager(
            @Parameter(name = "Manager", description = "Manager_obj to add") @RequestBody PersonWriteTO manager) {
        return managerService.addManager(manager);
    }

    @Operation(summary = "Update Manager")
    @ApiResponse(responseCode = "200", description = "News Manager",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = ManagerReadTO.class))})
    @PutMapping("")
    public ManagerReadTO updateManager(@Parameter(name = "Manager", description = "Manager_obj to update") @RequestBody Manager manager) {
        return managerService.updateManager(manager);
    }

    @Operation(summary = "Delete a Manager by its id")
    @ApiResponse(responseCode = "200", description = "News Manager",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = String.class))})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteManager(@Parameter(name = "ManagerId", description = "Id of the Manager to delete") @PathVariable UUID id) {
        return managerService.deleteManagerById(id);
    }
}
