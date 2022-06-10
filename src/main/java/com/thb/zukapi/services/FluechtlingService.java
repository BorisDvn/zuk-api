package com.thb.zukapi.services;

import com.thb.zukapi.exception.ApiRequestException;
import com.thb.zukapi.models.Fluechtling;
import com.thb.zukapi.repositories.FluechtlingRepository;
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
public class FluechtlingService {
    private final Logger logger = LoggerFactory.getLogger(FluechtlingService.class);

    @Autowired
    private FluechtlingRepository fluechtlingRepository;

    public Fluechtling getFluechtling(UUID id) {
        return fluechtlingRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Cannot find Fluechtling with id: " + id));
    }

    public List<Fluechtling> getAll(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Fluechtling> pagedResult = fluechtlingRepository.findAll(paging);

        return pagedResult.getContent();
    }

    public Fluechtling addFluechtling(Fluechtling fluechtling) {

        Fluechtling newFluechtling = new Fluechtling();

        newFluechtling.setNachname(fluechtling.getNachname());
        newFluechtling.setVorname(fluechtling.getVorname());
        newFluechtling.setGeburtsdatum(fluechtling.getGeburtsdatum());
        newFluechtling.setEmail(fluechtling.getEmail());
        newFluechtling.setAdresse(fluechtling.getAdresse());

        if (fluechtling.getStaatsangeroerigkeit() != null)
            newFluechtling.setStaatsangeroerigkeit(fluechtling.getStaatsangeroerigkeit());
        if (fluechtling.getTelefonnummer() != null)
            newFluechtling.setTelefonnummer(fluechtling.getTelefonnummer());

        return fluechtlingRepository.save(newFluechtling);
    }

    public Fluechtling updateFluechtling(Fluechtling fluechtling) {

        Fluechtling fluechtlingToUpdate = getFluechtling(fluechtling.getId());

        if (fluechtling.getNachname() != null)
            fluechtlingToUpdate.setNachname(fluechtling.getNachname());
        if (fluechtling.getVorname() != null)
            fluechtlingToUpdate.setVorname(fluechtling.getVorname());
        if (fluechtling.getStaatsangeroerigkeit() != null)
            fluechtlingToUpdate.setStaatsangeroerigkeit(fluechtling.getStaatsangeroerigkeit());
        if (fluechtling.getGeburtsdatum() != null)
            fluechtlingToUpdate.setGeburtsdatum(fluechtling.getGeburtsdatum());
        if (fluechtling.getTelefonnummer() != null)
            fluechtlingToUpdate.setTelefonnummer(fluechtling.getTelefonnummer());
        if (fluechtling.getEmail() != null)
            fluechtlingToUpdate.setEmail(fluechtling.getEmail());
        if (fluechtling.getAdresse() != null)
            fluechtlingToUpdate.setAdresse(fluechtling.getAdresse());

        return fluechtlingRepository.save(fluechtlingToUpdate);
    }

    public ResponseEntity<String> deleteFluechtlingById(UUID id) {
        Fluechtling fluechtlingToDelete = getFluechtling(id);

        fluechtlingRepository.deleteById(fluechtlingToDelete.getId());

        logger.info("Fluechtling successfully deleted");
        return new ResponseEntity<>("Fluechtling successfully deleted", HttpStatus.OK);
    }

}
