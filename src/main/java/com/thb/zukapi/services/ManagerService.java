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
import com.thb.zukapi.models.Manager;
import com.thb.zukapi.models.User;
import com.thb.zukapi.repositories.ManagerRepository;

@Service
public class ManagerService {
	private final Logger logger = LoggerFactory.getLogger(ManagerService.class);

	@Autowired
	private ManagerRepository managerRepository;

	@Autowired
	private UserService userService;

	public Manager getManager(UUID id) {
		return findManager(id);
	}

	public List<Manager> getAll(Integer pageNo, Integer pageSize, String sortBy) {

		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<Manager> pagedResult = managerRepository.findAll(paging);

		return pagedResult.getContent();
	}

	public Manager addManager(PersonWriteTO manager) {

		// Create System User
		User user = userService.signUp(manager);

		Manager newManager = new Manager();

		newManager.setUser(user);
		newManager.setEmail(user.getEmail());
		newManager.setLastname(manager.getLastname());
		newManager.setFirstname(manager.getFirstname());
		if (manager.getNationality() != null) {
			newManager.setNationality(manager.getNationality());
		}
		newManager.setDob(manager.getDob());
		newManager.setPhone(manager.getPhone());
		newManager.setAdresse(manager.getAdresse());
		newManager.setGender(manager.getGender());

		return managerRepository.save(newManager);
	}

	public Manager updateManager(Manager manager) {

		Manager managerToUpdate = getManager(manager.getId());

		if (manager.getLastname() != null)
			managerToUpdate.setLastname(manager.getLastname());
		if (manager.getFirstname() != null)
			managerToUpdate.setFirstname(manager.getFirstname());
		if (manager.getNationality() != null)
			managerToUpdate.setNationality(manager.getNationality());
		if (manager.getDob() != null) // TODO: check
			managerToUpdate.setDob(manager.getDob());
		if (manager.getPhone() != null)
			managerToUpdate.setPhone(manager.getPhone());
		if (manager.getEmail() != null)
			managerToUpdate.setEmail(manager.getEmail());
		if (manager.getAdresse() != null)
			managerToUpdate.setAdresse(manager.getAdresse());
		if (manager.getGender() != null)
			managerToUpdate.setGender(manager.getGender());

		return managerRepository.save(managerToUpdate);
	}

	public ResponseEntity<String> deleteManagerById(UUID id) {
		Manager managerToDelete = getManager(id);

		managerRepository.deleteById(managerToDelete.getId());

		logger.info("Manager successfully deleted");
		return new ResponseEntity<>("Manager successfully deleted", HttpStatus.OK);
	}

	public Manager findManager(UUID id) {
		return managerRepository.findById(id)
				.orElseThrow(() -> new ApiRequestException("Cannot find Manager with id: " + id));
	}

}
