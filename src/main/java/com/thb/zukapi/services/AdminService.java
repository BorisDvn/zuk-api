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

import com.thb.zukapi.dtos.person.PersonWriteTO;
import com.thb.zukapi.exception.ApiRequestException;
import com.thb.zukapi.models.Admin;
import com.thb.zukapi.models.User;
import com.thb.zukapi.repositories.AdminRepository;

@Service
public class AdminService {
	private final Logger logger = LoggerFactory.getLogger(AdminService.class);

	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private UserService userService;

	public Admin getAdmin(UUID id) {
		return findAdmin(id);
	}

	public List<Admin> getAll(Integer pageNo, Integer pageSize, String sortBy) {

		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<Admin> pagedResult = adminRepository.findAll(paging);

		return pagedResult.getContent();
	}

	public Admin addAdmin(PersonWriteTO admin) {
		
		// Create System User
		User user = userService.signUp(admin);

		Admin newAdmin = new Admin();
		newAdmin.setUser(user);
		newAdmin.setEmail(user.getEmail());
		newAdmin.setLastname(admin.getLastname());
		newAdmin.setFirstname(admin.getFirstname());
		if (admin.getNationality() != null) {
			newAdmin.setNationality(admin.getNationality());
		}
		newAdmin.setDob(admin.getDob());
		newAdmin.setPhone(admin.getPhone());
		newAdmin.setEmail(admin.getEmail());
		newAdmin.setAdresse(admin.getAdresse());
		newAdmin.setGender(admin.getGender());

		return adminRepository.save(newAdmin);
	}

	public Admin updateAdmin(Admin admin) {

		Admin adminToUpdate = getAdmin(admin.getId());

		if (admin.getLastname() != null)
			adminToUpdate.setLastname(admin.getLastname());
		if (admin.getFirstname() != null)
			adminToUpdate.setFirstname(admin.getFirstname());
		if (admin.getNationality() != null)
			adminToUpdate.setNationality(admin.getNationality());
		if (admin.getDob() != null) // TODO: check
			adminToUpdate.setDob(admin.getDob());
		if (admin.getPhone() != null)
			adminToUpdate.setPhone(admin.getPhone());
		if (admin.getEmail() != null)
			adminToUpdate.setEmail(admin.getEmail());
		if (admin.getAdresse() != null)
			adminToUpdate.setAdresse(admin.getAdresse());
		if (admin.getGender() != null)
			adminToUpdate.setGender(admin.getGender());

		return adminRepository.save(adminToUpdate);
	}

	public ResponseEntity<String> deleteAdminById(UUID id) {
		Admin adminToDelete = getAdmin(id);

		adminRepository.deleteById(adminToDelete.getId());

		logger.info("Admin successfully deleted");
		return new ResponseEntity<>("Admin successfully deleted", HttpStatus.OK);
	}
	
	public Admin findAdmin(UUID id) {
		return adminRepository.findById(id)
				.orElseThrow(() -> new ApiRequestException("Cannot find Admin with id: " + id));
	}

}