package com.thb.zukapi.controller;

import com.thb.zukapi.models.Kategorie;
import com.thb.zukapi.services.KategorieService;
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
@RequestMapping("zuk-api/v1/kategorien")
@CrossOrigin(origins = "*")
public class KategorieController {
    @Autowired
    protected KategorieService kategorieService;

    @Operation(summary = "Get All Kategorie")
    @ApiResponse(responseCode = "200", description = "Found all Kategorie",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Kategorie.class))})
    @GetMapping("")
    public List<Kategorie> getAllKategorie(@RequestParam(defaultValue = "0") Integer pageNo,
                                           @RequestParam(defaultValue = "10") Integer pageSize,
                                           @RequestParam(defaultValue = "name") String sortBy) {
        return kategorieService.getAll(pageNo, pageSize, sortBy);
    }

    @Operation(summary = "Get a Kategorie by its id")
    @ApiResponse(responseCode = "200", description = "Found the Kategorie",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Kategorie.class))})
    @GetMapping("/{id}")
    public Kategorie getKategorieById(@Parameter(name = "KategorieId", description = "ID of the Kategorie_obj") @PathVariable UUID id) {
        return kategorieService.getKategorie(id);
    }

    @Operation(summary = "Add One Kategorie")
    @ApiResponse(responseCode = "200", description = "Kategorie added",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Kategorie.class))})
    @PostMapping("")
    public Kategorie addKategorie(
            @Parameter(name = "Kategorie", description = "Kategorie_obj to add") @RequestBody Kategorie kategorie) {
        return kategorieService.addKategorie(kategorie);
    }

    @Operation(summary = "Update Kategorie")
    @ApiResponse(responseCode = "200", description = "News Kategorie",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Kategorie.class))})
    @PutMapping("")
    public Kategorie updateKategorie(@Parameter(name = "Kategorie", description = "Kategorie_obj to update") @RequestBody Kategorie kategorie) {
        return kategorieService.updateKategorie(kategorie);
    }

    @Operation(summary = "Delete a Kategorie by its id")
    @ApiResponse(responseCode = "200", description = "News Kategorie",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Kategorie.class))})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteKategorie(@Parameter(name = "KategorieId", description = "Id of the Kategorie to delete") @PathVariable UUID id) {
        return kategorieService.deleteKategorieById(id);
    }
}
