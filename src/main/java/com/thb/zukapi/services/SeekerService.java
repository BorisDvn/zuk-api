package com.thb.zukapi.services;

import com.thb.zukapi.exception.ApiRequestException;
import com.thb.zukapi.models.RoleType;
import com.thb.zukapi.models.Seeker;
import com.thb.zukapi.repositories.SeekerRepository;
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
public class SeekerService {
    private final Logger logger = LoggerFactory.getLogger(SeekerService.class);

    @Autowired
    private SeekerRepository seekerRepository;

    public Seeker getSeeker(UUID id) {
        return seekerRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Cannot find Seeker with id: " + id));
    }

    public List<Seeker> getAll(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Seeker> pagedResult = seekerRepository.findAll(paging);

        return pagedResult.getContent();
    }

    public Seeker addSeeker(Seeker seeker) {

        Seeker newSeeker = new Seeker();

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
        newSeeker.setPassword(seeker.getPassword()); // TODO: encrypt
        newSeeker.setRole(RoleType.SEEKER);

        return seekerRepository.save(newSeeker);
    }

    public Seeker updateSeeker(Seeker seeker) {

        Seeker seekerToUpdate = getSeeker(seeker.getId());

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
        if (seeker.getPassword() != null)
            seekerToUpdate.setPassword(seeker.getPassword()); // TODO: encrypt

        return seekerRepository.save(seekerToUpdate);
    }

    public ResponseEntity<String> deleteSeekerById(UUID id) {
        Seeker seekerToDelete = getSeeker(id);

        seekerRepository.deleteById(seekerToDelete.getId());

        logger.info("Seeker successfully deleted");
        return new ResponseEntity<>("Seeker successfully deleted", HttpStatus.OK);
    }

}
