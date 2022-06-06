package com.thb.zukapi.controller;

import com.thb.zukapi.models.Information;
import com.thb.zukapi.services.InformationService;
import io.swagger.v3.oas.annotations.Operation;
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

    @Operation(summary ="Get All Information")
    @GetMapping("")
    public List<Information> getAllInformation(@RequestParam(defaultValue = "0") Integer pageNo,
                                               @RequestParam(defaultValue = "10") Integer pageSize,
                                               @RequestParam(defaultValue = "name") String sortBy) {
        return informationService.getAll(pageNo, pageSize, sortBy);
    }

    /*@Operation(summary ="Get One Information")
    @GetMapping("/{id}")
    public Information getInformationById(@ApiParam(name = "InformationId", value = "ID of the Information_obj") @PathVariable UUID id) {
        return informationService.getAdmin(id);
    }

    @Operation(summary ="Add One Information")
    @PostMapping("")
    public Information addInformation(
            @ApiParam(name = "Information", value = "Information to add") @RequestBody Information information) {
        return informationService.addInformation(information);
    }

    @Operation(summary ="Update Information")
    @PutMapping("")
    public Information updateInformation(@ApiParam(name = "Information", value = "Information to update") @RequestBody Information information) {
        return informationService.updateInformation(information);
    }

    @Operation(summary ="Delete Information")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInformation(@ApiParam(name = "InformationId", value = "Id of the Information") @PathVariable UUID id) {
        return informationService.deleteInformation(id);
    }*/
}
