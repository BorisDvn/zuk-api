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

        newHelfer.setNachname(helfer.getNachname());
        newHelfer.setVorname(helfer.getVorname());
        newHelfer.setGeburtsdatum(helfer.getGeburtsdatum());
        newHelfer.setEmail(helfer.getEmail());
        newHelfer.setAdresse(helfer.getAdresse());

        if (helfer.getStaatsangeroerigkeit() != null)
            newHelfer.setStaatsangeroerigkeit(helfer.getStaatsangeroerigkeit());
        if (helfer.getTelefonnummer() != null)
            newHelfer.setTelefonnummer(helfer.getTelefonnummer());

        logger.info("Helfer successfully added");
        return helferRepository.save(newHelfer);
    }

    public Helfer updateHelfer(Helfer helfer) {

        Helfer helferToUpdate = getHelfer(helfer.getId());

        if (helfer.getNachname() != null)
            helferToUpdate.setNachname(helfer.getNachname());
        if (helfer.getVorname() != null)
            helferToUpdate.setVorname(helfer.getVorname());
        if (helfer.getStaatsangeroerigkeit() != null)
            helferToUpdate.setStaatsangeroerigkeit(helfer.getStaatsangeroerigkeit());
        if (helfer.getGeburtsdatum() != null)
            helferToUpdate.setGeburtsdatum(helfer.getGeburtsdatum());
        if (helfer.getTelefonnummer() != null)
            helferToUpdate.setTelefonnummer(helfer.getTelefonnummer());
        if (helfer.getEmail() != null)
            helferToUpdate.setEmail(helfer.getEmail());
        if (helfer.getAdresse() != null)
            helferToUpdate.setAdresse(helfer.getAdresse());

        return helferRepository.save(helferToUpdate);
    }

    public ResponseEntity<String> deleteHelferById(UUID id) {
        Helfer helferToDelete = getHelfer(id);

        helferRepository.deleteById(helferToDelete.getId());

        logger.info("Helfer successfully deleted");
        return new ResponseEntity<>("Helfer successfully deleted", HttpStatus.OK);
    }
}
