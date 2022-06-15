package com.thb.zukapi.services;

import com.thb.zukapi.exception.ApiRequestException;
import com.thb.zukapi.models.Helper;
import com.thb.zukapi.repositories.HelferRepository;
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
public class HelferService {
    private final Logger logger = LoggerFactory.getLogger(HelferService.class);

    @Autowired
    private HelferRepository helferRepository;

    public Helper getHelfer(UUID id) {
        return helferRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Cannot find Helper with id: " + id));
    }

    public List<Helper> getAll(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Helper> pagedResult = helferRepository.findAll(paging);

        return pagedResult.getContent();
    }

    public Helper addHelfer(Helper helper) {

        Helper newHelper = new Helper();

        newHelper.setLastname(helper.getLastname());
        newHelper.setFirstname(helper.getFirstname());
        newHelper.setDob(helper.getDob());
        newHelper.setEmail(helper.getEmail());
        newHelper.setAdresse(helper.getAdresse());
        newHelper.setHelperStatus(helper.getHelperStatus());

        if (helper.getNationality() != null)
            newHelper.setNationality(helper.getNationality());
        if (helper.getPhone() != null)
            newHelper.setPhone(helper.getPhone());

        return helferRepository.save(newHelper);
    }

    public Helper updateHelfer(Helper helper) {

        Helper helperToUpdate = getHelfer(helper.getId());

        if (helper.getLastname() != null)
            helperToUpdate.setLastname(helper.getLastname());
        if (helper.getFirstname() != null)
            helperToUpdate.setFirstname(helper.getFirstname());
        if (helper.getNationality() != null)
            helperToUpdate.setNationality(helper.getNationality());
        if (helper.getDob() != null)
            helperToUpdate.setDob(helper.getDob());
        if (helper.getPhone() != null)
            helperToUpdate.setPhone(helper.getPhone());
        if (helper.getEmail() != null)
            helperToUpdate.setEmail(helper.getEmail());
        if (helper.getAdresse() != null)
            helperToUpdate.setAdresse(helper.getAdresse());
        if (helper.getHelperStatus() != null)
            helperToUpdate.setHelperStatus(helper.getHelperStatus());

        return helferRepository.save(helperToUpdate);
    }

    public ResponseEntity<String> deleteHelferById(UUID id) {
        Helper helperToDelete = getHelfer(id);

        helferRepository.deleteById(helperToDelete.getId());

        logger.info("Helper successfully deleted");
        return new ResponseEntity<>("Helper successfully deleted", HttpStatus.OK);
    }
}
