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

import com.thb.zukapi.dtos.manager.Manager2ManagerReadListTO;
import com.thb.zukapi.dtos.manager.Manager2ManagerReadTO;
import com.thb.zukapi.dtos.manager.ManagerReadListTO;
import com.thb.zukapi.dtos.manager.ManagerReadTO;
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

	public ManagerReadTO getManager(UUID id) {
		return Manager2ManagerReadTO.apply(findManager(id));
	}

	public List<ManagerReadListTO> getAll(Integer pageNo, Integer pageSize, String sortBy) {

		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<Manager> pagedResult = managerRepository.findAll(paging);

		return Manager2ManagerReadListTO.apply(pagedResult.getContent());
	}

	public ManagerReadTO addManager(PersonWriteTO manager) {

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
		newManager.setAddress(manager.getAddress());
		newManager.setGender(manager.getGender());

		return Manager2ManagerReadTO.apply(managerRepository.save(newManager));
	}

	public ManagerReadTO updateManager(Manager manager) {

		Manager managerToUpdate = findManager(manager.getId());

		if (manager.getLastname() != null)
			managerToUpdate.setLastname(manager.getLastname());
		if (manager.getFirstname() != null)
			managerToUpdate.setFirstname(manager.getFirstname());
		if (manager.getNationality() != null)
			managerToUpdate.setNationality(manager.getNationality());
		if (manager.getDob() != null)
			managerToUpdate.setDob(manager.getDob());
		if (manager.getPhone() != null)
			managerToUpdate.setPhone(manager.getPhone());
		if (manager.getEmail() != null)
			managerToUpdate.setEmail(manager.getEmail());
		if (manager.getAddress() != null)
			managerToUpdate.setAddress(manager.getAddress());
		if (manager.getGender() != null)
			managerToUpdate.setGender(manager.getGender());

		return Manager2ManagerReadTO.apply(managerRepository.save(managerToUpdate));
	}

	public ResponseEntity<String> deleteManagerById(UUID id) {
		Manager managerToDelete = findManager(id);

		managerRepository.deleteById(managerToDelete.getId());

		logger.info("Manager successfully deleted");
		return new ResponseEntity<>("Manager successfully deleted", HttpStatus.OK);
	}

	public Manager findManager(UUID id) {
		return managerRepository.findById(id)
				.orElseThrow(() -> new ApiRequestException("Cannot find Manager with id: " + id));
	}

}
