package com.thb.zukapi.services;

import com.thb.zukapi.exception.ApiRequestException;
import com.thb.zukapi.models.Helfer;
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

    public Helfer getHelfer(UUID id) {
        return helferRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Cannot find Helfer with id: " + id));
    }

    public List<Helfer> getAll(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Helfer> pagedResult = helferRepository.findAll(paging);

        return pagedResult.getContent();
    }

    public Helfer addHelfer(Helfer helfer) {

        Helfer newHelfer = new Helfer();

        newHelfer.setLastname(helfer.getLastname());
        newHelfer.setFirstname(helfer.getFirstname());
        newHelfer.setDob(helfer.getDob());
        newHelfer.setEmail(helfer.getEmail());
        newHelfer.setAdresse(helfer.getAdresse());
        newHelfer.setHelferStatus(helfer.getHelferStatus());

        if (helfer.getNationality() != null)
            newHelfer.setNationality(helfer.getNationality());
        if (helfer.getPhone() != null)
            newHelfer.setPhone(helfer.getPhone());

        return helferRepository.save(newHelfer);
    }

    public Helfer updateHelfer(Helfer helfer) {

        Helfer helferToUpdate = getHelfer(helfer.getId());

        if (helfer.getLastname() != null)
            helferToUpdate.setLastname(helfer.getLastname());
        if (helfer.getFirstname() != null)
            helferToUpdate.setFirstname(helfer.getFirstname());
        if (helfer.getNationality() != null)
            helferToUpdate.setNationality(helfer.getNationality());
        if (helfer.getDob() != null)
            helferToUpdate.setDob(helfer.getDob());
        if (helfer.getPhone() != null)
            helferToUpdate.setPhone(helfer.getPhone());
        if (helfer.getEmail() != null)
            helferToUpdate.setEmail(helfer.getEmail());
        if (helfer.getAdresse() != null)
            helferToUpdate.setAdresse(helfer.getAdresse());
        if (helfer.getHelferStatus() != null)
            helferToUpdate.setHelferStatus(helfer.getHelferStatus());

        return helferRepository.save(helferToUpdate);
    }

    public ResponseEntity<String> deleteHelferById(UUID id) {
        Helfer helferToDelete = getHelfer(id);

        helferRepository.deleteById(helferToDelete.getId());

        logger.info("Helfer successfully deleted");
        return new ResponseEntity<>("Helfer successfully deleted", HttpStatus.OK);
    }
}
