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
import com.thb.zukapi.dtos.seeker.Seeker2SeekerReadListTO;
import com.thb.zukapi.dtos.seeker.Seeker2SeekerReadTO;
import com.thb.zukapi.dtos.seeker.SeekerReadListTO;
import com.thb.zukapi.dtos.seeker.SeekerReadTO;
import com.thb.zukapi.exception.ApiRequestException;
import com.thb.zukapi.models.Seeker;
import com.thb.zukapi.models.User;
import com.thb.zukapi.repositories.SeekerRepository;

@Service
public class SeekerService {

	private final Logger logger = LoggerFactory.getLogger(SeekerService.class);

	@Autowired
	private SeekerRepository seekerRepository;

	@Autowired
	private UserService userService;

	public SeekerReadTO getSeeker(UUID id) {
		return Seeker2SeekerReadTO.apply(findSeeker(id));
	}

	public List<SeekerReadListTO> getAll(Integer pageNo, Integer pageSize, String sortBy) {

		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<Seeker> pagedResult = seekerRepository.findAll(paging);

		return Seeker2SeekerReadListTO.apply(pagedResult.getContent());
	}

	public Seeker addSeeker(PersonWriteTO seeker) {

		// Create System User
		User user = userService.signUp(seeker);

		Seeker newSeeker = new Seeker();
		newSeeker.setUser(user);
		newSeeker.setEmail(user.getEmail());
		newSeeker.setLastname(seeker.getLastname());
		newSeeker.setFirstname(seeker.getFirstname());
		if (seeker.getNationality() != null) {
			newSeeker.setNationality(seeker.getNationality());
		}
		newSeeker.setDob(seeker.getDob());
		newSeeker.setPhone(seeker.getPhone());
		newSeeker.setEmail(seeker.getEmail());
		newSeeker.setAdresse(seeker.getAdresse());
		newSeeker.setGender(seeker.getGender());

		return seekerRepository.save(newSeeker);
	}

	public Seeker updateSeeker(Seeker seeker) {

		Seeker seekerToUpdate = findSeeker(seeker.getId());

		if (seeker.getLastname() != null)
			seekerToUpdate.setLastname(seeker.getLastname());
		if (seeker.getFirstname() != null)
			seekerToUpdate.setFirstname(seeker.getFirstname());
		if (seeker.getNationality() != null)
			seekerToUpdate.setNationality(seeker.getNationality());
		if (seeker.getDob() != null) // TODO: check
			seekerToUpdate.setDob(seeker.getDob());
		if (seeker.getPhone() != null)
			seekerToUpdate.setPhone(seeker.getPhone());
		if (seeker.getEmail() != null)
			seekerToUpdate.setEmail(seeker.getEmail());
		if (seeker.getAdresse() != null)
			seekerToUpdate.setAdresse(seeker.getAdresse());
		if (seeker.getGender() != null)
			seekerToUpdate.setGender(seeker.getGender());

		return seekerRepository.save(seekerToUpdate);
	}

	public ResponseEntity<String> deleteSeekerById(UUID id) {
		Seeker seekerToDelete = findSeeker(id);

		seekerRepository.deleteById(seekerToDelete.getId());

		logger.info("Seeker successfully deleted");
		return new ResponseEntity<>("Seeker successfully deleted", HttpStatus.OK);
	}

	public Seeker findSeeker(UUID id) {
		return seekerRepository.findById(id)
				.orElseThrow(() -> new ApiRequestException("Cannot find Seeker with id: " + id));
	}

}

