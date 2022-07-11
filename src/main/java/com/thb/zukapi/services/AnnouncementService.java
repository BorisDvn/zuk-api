package com.thb.zukapi.services;

import java.util.ArrayList;
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
import org.springframework.web.multipart.MultipartFile;

import com.thb.zukapi.dtos.announcements.Announcement2AnnouncementReadListTO;
import com.thb.zukapi.dtos.announcements.Announcement2AnnouncementReadTO;
import com.thb.zukapi.dtos.announcements.AnnouncementReadListTO;
import com.thb.zukapi.dtos.announcements.AnnouncementReadTO;
import com.thb.zukapi.dtos.announcements.AnnouncementWriteTO;
import com.thb.zukapi.dtos.files.FileTO;
import com.thb.zukapi.exception.ApiRequestException;
import com.thb.zukapi.models.Announcement;
import com.thb.zukapi.models.AnnouncementStatus;
import com.thb.zukapi.models.Category;
import com.thb.zukapi.models.File;
import com.thb.zukapi.repositories.AnnouncementRepository;
import com.thb.zukapi.repositories.FileRepository;
import com.thb.zukapi.utils.FileUpload;

@Service
public class AnnouncementService {

	private final Logger logger = LoggerFactory.getLogger(AnnouncementService.class);

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private AdminService adminService;

	@Autowired
	private ManagerService managerService;

	@Autowired
	private HelperService helperService;

	@Autowired
	private SeekerService seekerService;

	@Autowired
	private FileUpload fileUpload;

	@Autowired
	private FileRepository fileRepo;

	@Autowired
	private AnnouncementRepository announcementRepository;

	public AnnouncementReadTO getAnnouncement(UUID id) {
		return Announcement2AnnouncementReadTO.apply(findAnnouncement(id));
	}

	public List<AnnouncementReadListTO> getAll(Integer pageNo, Integer pageSize, String sortBy) {

		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<Announcement> pagedResult = announcementRepository.findAll(paging);

		return Announcement2AnnouncementReadListTO.apply(pagedResult.getContent());
	}
	
	public List<AnnouncementReadTO> getAnnouncementByCategory(String catName) {

		List<Announcement> pagedResult = announcementRepository.findAnnouncementByCategory_Name(catName);

		return Announcement2AnnouncementReadTO.apply(pagedResult);
	}

	public AnnouncementReadTO addAnnouncement(AnnouncementWriteTO announcement, List<MultipartFile> files) {

		// find the category
		Category category = categoryService.findCategory(announcement.getCategoryId());

		Announcement newAnnouncement = new Announcement();

		newAnnouncement.setTitle(announcement.getTitle());

		newAnnouncement.setDescription(announcement.getDescription());

		// standby as default value
		newAnnouncement.setStatus(AnnouncementStatus.STANDBY);

		newAnnouncement.setCategory(category);

		// set images
		List<File> uploadedFiles = new ArrayList<>();

		if (files.size() > 0) {
			for (MultipartFile file : files) {
				FileTO response = fileUpload.uploadToFileService(file, FileUpload.uploadFolder);

				File cover = new File();
				cover.setFileLink(response.getFileLink());
				cover.setName(response.getFilename());
				cover = fileRepo.save(cover);
				uploadedFiles.add(cover);
			}
		}

		newAnnouncement.setImages(uploadedFiles);

		// set the creator according the usertype
		switch (announcement.getCreatorStatus().toString()) {
		case "SEEKER":
			newAnnouncement.setSeeker(seekerService.findSeeker(announcement.getCreatorId()));
			break;
		case "HELPER":
			newAnnouncement.setHelper(helperService.findHelper(announcement.getCreatorId()));
			break;
		case "ADMIN":
			newAnnouncement.setAdmin(adminService.findAdmin(announcement.getCreatorId()));
			break;
		case "MANAGER":
			newAnnouncement.setManager(managerService.getManager(announcement.getCreatorId()));
			break;
		}

		return Announcement2AnnouncementReadTO.apply(announcementRepository.save(newAnnouncement));
	}

	public AnnouncementReadTO updateAnnouncement(AnnouncementWriteTO announcement) {

		Announcement announcementToUpdate = findAnnouncement(announcement.getId());

		// find the category
		Category category = categoryService.findCategory(announcement.getCategoryId());

		announcementToUpdate.setTitle(announcement.getTitle());

		announcementToUpdate.setDescription(announcement.getDescription());

		// standby as default value
		announcementToUpdate.setStatus(announcement.getStatus());

		announcementToUpdate.setCategory(category);

		// set the creator accordind the user type
		switch (announcement.getCreatorStatus().toString()) {
		case "SEEKER":
			announcementToUpdate.setSeeker(seekerService.findSeeker(announcement.getCreatorId()));
			break;
		case "HELPER":
			announcementToUpdate.setHelper(helperService.findHelper(announcement.getCreatorId()));
			break;
		case "ADMIN":
			announcementToUpdate.setAdmin(adminService.findAdmin(announcement.getCreatorId()));
			break;
		case "MANAGER":
			announcementToUpdate.setManager(managerService.getManager(announcement.getCreatorId()));
			break;
		}

		return Announcement2AnnouncementReadTO.apply(announcementRepository.save(announcementToUpdate));
	}

	// manage images in another endpoint and not in updateFunction to simplefy the
	// complexity
	public AnnouncementReadTO addImageAnnouncement(UUID announcementId, MultipartFile file) {

		Announcement announcementToUpdate = findAnnouncement(announcementId);

		// if the file already exist just use it
		if (fileRepo.findByName(file.getOriginalFilename()).isPresent()) {
			announcementToUpdate.getImages().add(fileRepo.findByName(file.getOriginalFilename()).get());
		} else {
			// else upload
			FileTO response = fileUpload.uploadToFileService(file, FileUpload.uploadFolder);

			File image = new File();
			image.setFileLink(response.getFileLink());
			image.setName(response.getFilename());
			image = fileRepo.save(image);

			announcementToUpdate.getImages().add(image);
		}
		return Announcement2AnnouncementReadTO.apply(announcementRepository.save(announcementToUpdate));
	}

	// manage images in another endpoint and not in updateFunction to simplefy the
	// complexity
	public AnnouncementReadTO removeImageAnnouncement(UUID announcementId, MultipartFile file) {

		Announcement announcementToUpdate = findAnnouncement(announcementId);

		// if the file already exist remove it
		if (fileRepo.findByName(file.getOriginalFilename()).isPresent()) {
			announcementToUpdate.getImages().remove(fileRepo.findByName(file.getOriginalFilename()).get());
		} else {
			throw new ApiRequestException(
					String.format("the file with name %s does not exist", file.getOriginalFilename()));
		}
		return Announcement2AnnouncementReadTO.apply(announcementRepository.save(announcementToUpdate));
	}

	public ResponseEntity<String> deleteAnnouncementById(UUID id) {
		Announcement announcementToDelete = findAnnouncement(id);

		announcementRepository.deleteById(announcementToDelete.getId());
		logger.info("successfully deleted");

		return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
	}

	public Announcement findAnnouncement(UUID id) {
		return announcementRepository.findById(id)
				.orElseThrow(() -> new ApiRequestException("Cannot find Announcement with id: " + id));
	}

}
