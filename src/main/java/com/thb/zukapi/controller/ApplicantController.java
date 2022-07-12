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

import com.thb.zukapi.dtos.applicants.ApplicantReadListTO;
import com.thb.zukapi.dtos.applicants.ApplicantReadTO;
import com.thb.zukapi.models.Applicant;
import com.thb.zukapi.services.ApplicantService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

@RestController
@RequestMapping("zuk-api/v1/applicant")
@CrossOrigin(origins = "*")
public class ApplicantController {
	@Autowired
	protected ApplicantService applicantService;

	@Operation(summary = "Get All Applicant")
	@ApiResponse(responseCode = "200", description = "Found all Applicant", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Applicant.class)) })
	@GetMapping("")
	public List<ApplicantReadListTO> getAllApplicant(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "name") String sortBy) {
		return applicantService.getAll(pageNo, pageSize, sortBy);
	}

	@Operation(summary = "Get a Applicant by its id")
	@ApiResponse(responseCode = "200", description = "Found the Applicant", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Applicant.class)) })
	@GetMapping("/{id}")
	public ApplicantReadTO getApplicantById(
			@Parameter(name = "ApplicantId", description = "ID of the Applicant_obj") @PathVariable UUID id) {
		return applicantService.getApplicant(id);
	}

	@Operation(summary = "Get a Applicant by Announcement id")
	@ApiResponse(responseCode = "200", description = "Found the Applicant", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Applicant.class)) })
	@GetMapping("announcement/{id}")
	public List<ApplicantReadListTO> getApplicantByAnnouncementId(
			@Parameter(name = "ApplicantId", description = "ID of the Applicant_obj") @PathVariable UUID id) {
		return applicantService.getApplicantByAnnouncementId(id);
	}

	@Operation(summary = "Get a Applicant by Seeker Email")
	@ApiResponse(responseCode = "200", description = "Found the Applicant", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Applicant.class)) })
	@GetMapping("seeker")
	public List<ApplicantReadListTO> getApplicantBySeekerEmail(
			@Parameter(name = "ApplicantId", description = "ID of the Applicant_obj") @RequestParam String email) {
		return applicantService.getApplicantBySeekerEmail(email);
	}

	@Operation(summary = "Get a Applicant by Email")
	@ApiResponse(responseCode = "200", description = "Found the Applicant", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Applicant.class)) })
	@GetMapping("email")
	public List<ApplicantReadListTO> getApplicantByEmail(
			@Parameter(name = "ApplicantId", description = "ID of the Applicant_obj") @RequestParam String email) {
		return applicantService.getApplicantBySeekerEmail(email);
	}

	@Operation(summary = "Add One Applicant")
	@ApiResponse(responseCode = "200", description = "Applicant added", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Applicant.class)) })
	@PostMapping("")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
	public ApplicantReadTO addApplicant(
			@Parameter(name = "Applicant", description = "Applicant_obj to add") @RequestBody ApplicantReadTO applicant) {
		return applicantService.addApplicant(applicant);
	}

	@Operation(summary = "Update Applicant")
	@ApiResponse(responseCode = "200", description = "News Applicant", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Applicant.class)) })
	@PutMapping("")
	@PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
	public ApplicantReadTO updateApplicant(
			@Parameter(name = "Applicant", description = "Applicant_obj to update") @RequestBody ApplicantReadTO applicant) {
		return applicantService.updateApplicant(applicant);
	}

	@Operation(summary = "Delete a Applicant by its id")
	@ApiResponse(responseCode = "200", description = "News Applicant", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = Applicant.class)) })
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<String> deleteApplicant(
			@Parameter(name = "ApplicantId", description = "Id of the Applicant to delete") @PathVariable UUID id) {
		return applicantService.deleteApplicantById(id);
	}
}
