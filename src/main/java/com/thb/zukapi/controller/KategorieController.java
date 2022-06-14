package com.thb.zukapi.controller;

import com.thb.zukapi.models.Category;
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

    @Operation(summary = "Get All Category")
    @ApiResponse(responseCode = "200", description = "Found all Category",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Category.class))})
    @GetMapping("")
    public List<Category> getAllKategorie(@RequestParam(defaultValue = "0") Integer pageNo,
                                          @RequestParam(defaultValue = "10") Integer pageSize,
                                          @RequestParam(defaultValue = "name") String sortBy) {
        return kategorieService.getAll(pageNo, pageSize, sortBy);
    }

    @Operation(summary = "Get a Category by its id")
    @ApiResponse(responseCode = "200", description = "Found the Category",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Category.class))})
    @GetMapping("/{id}")
    public Category getKategorieById(@Parameter(name = "KategorieId", description = "ID of the Kategorie_obj") @PathVariable UUID id) {
        return kategorieService.getKategorie(id);
    }

    @Operation(summary = "Add One Category")
    @ApiResponse(responseCode = "200", description = "Category added",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Category.class))})
    @PostMapping("")
    public Category addKategorie(
            @Parameter(name = "Category", description = "Kategorie_obj to add") @RequestBody Category category) {
        return kategorieService.addKategorie(category);
    }

    @Operation(summary = "Update Category")
    @ApiResponse(responseCode = "200", description = "Announcement Category",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Category.class))})
    @PutMapping("")
    public Category updateKategorie(@Parameter(name = "Category", description = "Kategorie_obj to update") @RequestBody Category category) {
        return kategorieService.updateKategorie(category);
    }

    @Operation(summary = "Delete a Category by its id")
    @ApiResponse(responseCode = "200", description = "Announcement Category",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Category.class))})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteKategorie(@Parameter(name = "KategorieId", description = "Id of the Category to delete") @PathVariable UUID id) {
        return kategorieService.deleteKategorieById(id);
    }
}
