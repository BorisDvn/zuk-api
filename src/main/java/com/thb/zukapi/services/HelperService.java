package com.thb.zukapi.services;

import com.thb.zukapi.exception.ApiRequestException;
import com.thb.zukapi.models.Helper;
import com.thb.zukapi.models.RoleType;
import com.thb.zukapi.repositories.HelperRepository;
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

import java.util.List;
import java.util.UUID;

@Service
public class HelperService {
    private final Logger logger = LoggerFactory.getLogger(HelperService.class);

    @Autowired
    private HelperRepository helperRepository;

    public Helper getHelper(UUID id) {
        return helperRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Cannot find Helper with id: " + id));
    }

    public List<Helper> getAll(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Helper> pagedResult = helperRepository.findAll(paging);

        return pagedResult.getContent();
    }

    public Helper addHelper(Helper helper) {

        Helper newHelper = new Helper();

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
        newHelper.setPassword(helper.getPassword()); // TODO: encrypt
        newHelper.setRole(RoleType.HELPER);
        newHelper.setHelperType(helper.getHelperType());

        return helperRepository.save(newHelper);
    }

    public Helper updateHelper(Helper helper) {

        Helper helperToUpdate = getHelper(helper.getId());

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
        if (helper.getPassword() != null)
            helperToUpdate.setPassword(helper.getPassword()); // TODO: encrypt
        if (helper.getHelperType() != null)
            helperToUpdate.setHelperType(helper.getHelperType());

        return helperRepository.save(helperToUpdate);
    }

    public ResponseEntity<String> deleteHelperById(UUID id) {
        Helper helperToDelete = getHelper(id);

        helperRepository.deleteById(helperToDelete.getId());

        logger.info("Helper successfully deleted");
        return new ResponseEntity<>("Helper successfully deleted", HttpStatus.OK);
    }
}
