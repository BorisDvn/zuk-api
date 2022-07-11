package com.thb.zukapi.services;

import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.thb.zukapi.dtos.applicants.Applicant2ApplicantReadListTO;
import com.thb.zukapi.dtos.applicants.Applicant2ApplicantReadTO;
import com.thb.zukapi.dtos.applicants.ApplicantReadListTO;
import com.thb.zukapi.dtos.applicants.ApplicantReadTO;
import com.thb.zukapi.exception.ApiRequestException;
import com.thb.zukapi.models.Announcement;
import com.thb.zukapi.models.Applicant;
import com.thb.zukapi.models.ContactStatus;
import com.thb.zukapi.models.Seeker;
import com.thb.zukapi.repositories.ApplicantRepository;

@Service
public class ApplicantService {
	private final Logger logger = LoggerFactory.getLogger(ApplicantService.class);

	@Autowired
	private ApplicantRepository applicantRepository;

	@Autowired
	private AnnouncementService announcementService;

	@Autowired
	private SeekerService seekerService;

	public ApplicantReadTO getApplicant(UUID id) {
		return Applicant2ApplicantReadTO.apply(findApplicant(id));
	}

	public List<ApplicantReadListTO> getAll(Integer pageNo, Integer pageSize, String sortBy) {

		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<Applicant> pagedResult = applicantRepository.findAll(paging);

		return Applicant2ApplicantReadListTO.apply(pagedResult.getContent());
	}

	public ApplicantReadTO addApplicant(ApplicantReadTO applicant) {

		Announcement announcement = announcementService.findAnnouncement(applicant.getAnnouncementId());

		Seeker seeker = null;

		Applicant newApplicant = new Applicant();

		newApplicant.setDetails(applicant.getDetails());
		newApplicant.setAnnouncement(announcement);
		newApplicant.setStatus(ContactStatus.UNREAD);

		newApplicant.setDeviceId(applicant.getDeviceId());

		if (applicant.getSeekerId() != null) {
			seeker = seekerService.findSeeker(applicant.getSeekerId());

			newApplicant.setSeeker(seeker);
		} else {
			newApplicant.setEmail(applicant.getEmail());
			newApplicant.setPhone(applicant.getPhone());
			newApplicant.setName(applicant.getName());
		}

		return Applicant2ApplicantReadTO.apply(applicantRepository.save(newApplicant));
	}

	public ApplicantReadTO updateApplicant(ApplicantReadTO applicant) {

		Applicant applicantToUpdate = findApplicant(applicant.getId());

		if (applicant.getDetails() != null)
			applicantToUpdate.setDetails(applicant.getDetails());

		if (applicant.getAnnouncementId() != null) {
			Announcement announcement = announcementService.findAnnouncement(applicant.getAnnouncementId());
			applicantToUpdate.setAnnouncement(announcement);
		}

		applicantToUpdate.setStatus(applicant.getStatus());

		applicantToUpdate.setEmail(applicant.getEmail());

		applicantToUpdate.setPhone(applicant.getPhone());

		applicantToUpdate.setName(applicant.getName());

		applicantToUpdate.setDeviceId(applicant.getDeviceId());

		if (applicant.getSeekerId() != null) {
			Seeker seeker = seekerService.findSeeker(applicant.getSeekerId());
			applicantToUpdate.setSeeker(seeker);
		}

		return Applicant2ApplicantReadTO.apply(applicantRepository.save(applicantToUpdate));
	}

	public List<ApplicantReadListTO> getApplicantByAnnouncementId(UUID id) {
		if (applicantRepository.findByAnnouncement_Id(id).size() > 0) {
			return Applicant2ApplicantReadListTO.apply(applicantRepository.findByAnnouncement_Id(id));
		} else {
			throw new ApiRequestException(
					String.format("Cannot find Applicants for the given AnnouncementId '%s' ", id));
		}
	}

	public List<ApplicantReadListTO> getApplicantBySeekerEmail(String email) {
		if (applicantRepository.findBySeeker_Email(email).size() > 0) {
			return Applicant2ApplicantReadListTO.apply(applicantRepository.findBySeeker_Email(email));
		} else {
			throw new ApiRequestException(String.format("Cannot find Applicants for the given email '%s' ", email));
		}
	}

	public ApplicantReadTO getApplicantByEmail(String email) {
		if (applicantRepository.findByEmail(email).isPresent()) {
			return Applicant2ApplicantReadTO.apply(applicantRepository.findByEmail(email).get());
		} else {
			throw new ApiRequestException(String.format("Cannot find Applicants for the given email '%s'", email));
		}
	}

	public ResponseEntity<String> deleteApplicantById(UUID id) {
		Applicant applicantToDelete = findApplicant(id);

		applicantRepository.deleteById(applicantToDelete.getId());

		logger.info("Applicant successfully deleted");
		return new ResponseEntity<>("Applicant successfully deleted", HttpStatus.OK);
	}

	public Applicant findApplicant(UUID id) {
		return applicantRepository.findById(id)
				.orElseThrow(() -> new ApiRequestException("Cannot find Applicant with id: " + id));
	}

}