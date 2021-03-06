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
import com.thb.zukapi.dtos.seeker.SeekerReadListTO;
import com.thb.zukapi.dtos.seeker.SeekerReadTO;
import com.thb.zukapi.services.SeekerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("zuk-api/v1/seeker")
@CrossOrigin(origins = "*")
public class SeekerController {
	@Autowired
	protected SeekerService seekerService;

	@Operation(summary = "Get All Seeker")
	@ApiResponse(responseCode = "200", description = "Found all Seeker", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = SeekerReadListTO.class)) })
	@GetMapping("")
	public List<SeekerReadListTO> getAllSeeker(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize,
			@RequestParam(defaultValue = "lastname") String sortBy) {
		return seekerService.getAll(pageNo, pageSize, sortBy);
	}

	@Operation(summary = "Get a Seeker by its id")
	@ApiResponse(responseCode = "200", description = "Found the Seeker", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = SeekerReadTO.class)) })
	@GetMapping("/{id}")
	public SeekerReadTO getSeekerById(
			@Parameter(name = "SeekerId", description = "ID of the Seeker_obj") @PathVariable UUID id) {
		return seekerService.getSeeker(id);
	}

	@Operation(summary = "Add One Seeker")
	@ApiResponse(responseCode = "200", description = "Seeker added", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = SeekerReadTO.class)) })
	@PostMapping("")
	public SeekerReadTO addSeeker(
			@Parameter(name = "Seeker", description = "Seeker_obj to add") @RequestBody PersonWriteTO seeker) {
		return seekerService.addSeeker(seeker);
	}

	@Operation(summary = "Update Seeker")
	@ApiResponse(responseCode = "200", description = "News Seeker", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = SeekerReadTO.class)) })
	@PutMapping("")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
	public SeekerReadTO updateSeeker(
			@Parameter(name = "Seeker", description = "Seeker_obj to update") @RequestBody PersonWriteTO seeker) {
		return seekerService.updateSeeker(seeker);
	}

	@Operation(summary = "Delete a Seeker by its id")
	@ApiResponse(responseCode = "200", description = "News Seeker", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = String.class)) })
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteSeeker(
			@Parameter(name = "SeekerId", description = "Id of the Seeker to delete") @PathVariable UUID id) {
		return seekerService.deleteSeekerById(id);
	}
}