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

import com.thb.zukapi.dtos.helper.Helper2HelperReadListTO;
import com.thb.zukapi.dtos.helper.Helper2HelperReadTO;
import com.thb.zukapi.dtos.helper.HelperReadListTO;
import com.thb.zukapi.dtos.helper.HelperReadTO;
import com.thb.zukapi.dtos.person.PersonWriteTO;
import com.thb.zukapi.exception.ApiRequestException;
import com.thb.zukapi.models.Helper;
import com.thb.zukapi.models.User;
import com.thb.zukapi.repositories.HelperRepository;

@Service
public class HelperService {

	private final Logger logger = LoggerFactory.getLogger(HelperService.class);

	@Autowired
	private HelperRepository helperRepository;

	@Autowired
	private UserService userService;

	public HelperReadTO getHelper(UUID id) {
		return Helper2HelperReadTO.apply(findHelper(id));
	}

	public List<HelperReadListTO> getAll(Integer pageNo, Integer pageSize, String sortBy) {

		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<Helper> pagedResult = helperRepository.findAll(paging);

		return Helper2HelperReadListTO.apply(pagedResult.getContent());
	}

	public HelperReadTO addHelper(PersonWriteTO helper) {

		// Create System User
		User user = userService.signUp(helper);

		Helper newHelper = new Helper();

		newHelper.setUser(user);
		newHelper.setEmail(user.getEmail());

		newHelper.setLastname(helper.getLastname());
		newHelper.setFirstname(helper.getFirstname());
		if (helper.getNationality() != null) {
			newHelper.setNationality(helper.getNationality());
		}
		newHelper.setDob(helper.getDob());
		newHelper.setPhone(helper.getPhone());
		newHelper.setEmail(helper.getEmail());
		newHelper.setAdresse(helper.getAdresse());
		newHelper.setGender(helper.getGender());
		newHelper.setHelperType(helper.getHelperType());

		return Helper2HelperReadTO.apply(helperRepository.save(newHelper));
	}

	public HelperReadTO updateHelper(PersonWriteTO helper) {

		Helper helperToUpdate = findHelper(helper.getId());

		if (helper.getLastname() != null)
			helperToUpdate.setLastname(helper.getLastname());
		if (helper.getFirstname() != null)
			helperToUpdate.setFirstname(helper.getFirstname());
		if (helper.getNationality() != null)
			helperToUpdate.setNationality(helper.getNationality());
		if (helper.getDob() != null) // TODO: check
			helperToUpdate.setDob(helper.getDob());
		if (helper.getPhone() != null)
			helperToUpdate.setPhone(helper.getPhone());
		if (helper.getEmail() != null)
			helperToUpdate.setEmail(helper.getEmail());
		if (helper.getAdresse() != null)
			helperToUpdate.setAdresse(helper.getAdresse());
		if (helper.getGender() != null)
			helperToUpdate.setGender(helper.getGender());

		return Helper2HelperReadTO.apply(helperRepository.save(helperToUpdate));
	}

	public ResponseEntity<String> deleteHelperById(UUID id) {
		Helper helperToDelete = findHelper(id);

		helperRepository.deleteById(helperToDelete.getId());

		logger.info("Helper successfully deleted");
		return new ResponseEntity<>("Helper successfully deleted", HttpStatus.OK);
	}

	public Helper findHelper(UUID id) {
		return helperRepository.findById(id)
				.orElseThrow(() -> new ApiRequestException("Cannot find Helper with id: " + id));
	}

}
