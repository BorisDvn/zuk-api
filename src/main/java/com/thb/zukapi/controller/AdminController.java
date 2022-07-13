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

import com.thb.zukapi.dtos.person.PersonWriteTO;
import com.thb.zukapi.models.Admin;
import com.thb.zukapi.services.AdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("zuk-api/v1/admin")
@CrossOrigin(origins = "*")
public class AdminController {
	@Autowired
	protected AdminService adminService;

	@Operation(summary = "Get All Admin")
	@ApiResponse(responseCode = "200", description = "Found all Admin", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Admin.class)) })
	@GetMapping("")
	public List<Admin> getAllAdmin(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "name") String sortBy) {
		return adminService.getAll(pageNo, pageSize, sortBy);
	}

	@Operation(summary = "Get a Admin by its id")
	@ApiResponse(responseCode = "200", description = "Found the Admin", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Admin.class)) })
	@GetMapping("/{id}")
	public Admin getAdminById(@Parameter(name = "AdminId", description = "ID of the Admin_obj") @PathVariable UUID id) {
		return adminService.getAdmin(id);
	}

	@Operation(summary = "Add One Admin")
	@ApiResponse(responseCode = "200", description = "Admin added", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Admin.class)) })
	@PostMapping("")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
	public Admin addAdmin(
			@Parameter(name = "Admin", description = "Admin_obj to add") @RequestBody PersonWriteTO admin) {
		return adminService.addAdmin(admin);
	}

	@Operation(summary = "Update Admin")
	@ApiResponse(responseCode = "200", description = "News Admin", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Admin.class)) })
	@PutMapping("")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
	public Admin updateAdmin(@Parameter(name = "Admin", description = "Admin_obj to update") @RequestBody Admin admin) {
		return adminService.updateAdmin(admin);
	}

	@Operation(summary = "Delete a Admin by its id")
	@ApiResponse(responseCode = "200", description = "News Admin", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Admin.class)) })
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteAdmin(
			@Parameter(name = "AdminId", description = "Id of the Admin to delete") @PathVariable UUID id) {
		return adminService.deleteAdminById(id);
	}
}
