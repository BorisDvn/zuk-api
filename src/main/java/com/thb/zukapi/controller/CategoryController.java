package com.thb.zukapi.controller;

import com.thb.zukapi.dtos.CategoryReadListTO;
import com.thb.zukapi.dtos.CategoryReadTO;
import com.thb.zukapi.models.Category;
import com.thb.zukapi.services.CategoryService;
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
@RequestMapping("zuk-api/v1/category")
@CrossOrigin(origins = "*")
public class CategoryController {
    @Autowired
    protected CategoryService categoryService;

    @Operation(summary = "Get All Category")
    @ApiResponse(responseCode = "200", description = "Found all Category",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CategoryReadListTO.class))})
    @GetMapping("")
    public List<CategoryReadListTO> getAllCategory(@RequestParam(defaultValue = "0") Integer pageNo,
                                                   @RequestParam(defaultValue = "15") Integer pageSize,
                                                   @RequestParam(defaultValue = "name") String sortBy) {
        return categoryService.getAll(pageNo, pageSize, sortBy);
    }

    @Operation(summary = "Get a Category by its id")
    @ApiResponse(responseCode = "200", description = "Found the Category",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = CategoryReadTO.class))})
    @GetMapping("/{id}")
    public CategoryReadTO getCategoryById(@Parameter(name = "CategoryId", description = "ID of the Category_obj") @PathVariable UUID id) {
        return categoryService.getCategory(id);
    }

    @Operation(summary = "Get a Category by its name")
    @ApiResponse(responseCode = "200", description = "Found the Category",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Category.class))})
    @GetMapping("name/{name}")
    public Category getCategoryByName(@Parameter(name = "CategoryName", description = "Name of the Category") @PathVariable String name) {
        return categoryService.getCategoryByName(name);
    }

    @Operation(summary = "Add One Category")
    @ApiResponse(responseCode = "200", description = "Category added",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Category.class))})
    @PostMapping("")
    public Category addCategory(
            @Parameter(name = "Category", description = "Category_obj to add") @RequestBody Category category) {
        return categoryService.addCategory(category);
    }

    @Operation(summary = "Update Category")
    @ApiResponse(responseCode = "200", description = "News Category",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Category.class))})
    @PutMapping("")
    public Category updateCategory(@Parameter(name = "Category", description = "Category_obj to update") @RequestBody Category category) {
        return categoryService.updateCategory(category);
    }

    @Operation(summary = "Delete a Category by its id")
    @ApiResponse(responseCode = "200", description = "News Category",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Category.class))})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@Parameter(name = "CategoryId", description = "Id of the Category to delete") @PathVariable UUID id) {
        return categoryService.deleteCategoryById(id);
    }
}