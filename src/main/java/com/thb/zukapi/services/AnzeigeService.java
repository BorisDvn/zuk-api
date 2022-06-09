package com.thb.zukapi.services;

import com.thb.zukapi.exception.ApiRequestException;
import com.thb.zukapi.models.Anzeige;
import com.thb.zukapi.models.ErstellerStatus;
import com.thb.zukapi.repositories.AnzeigeRepository;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AnzeigeService {
    private final Logger logger = LoggerFactory.getLogger(AnzeigeService.class);

    @Autowired
    private AnzeigeRepository anzeigeRepository;

    public Anzeige getAnzeige(UUID id) {
        return anzeigeRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Cannot find Anzeige with id: " + id));
    }

    public List<Anzeige> getAll(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Anzeige> pagedResult = anzeigeRepository.findAll(paging);

        return pagedResult.getContent();
    }

    public Anzeige addAnzeige(Anzeige anzeige) {

        Anzeige newAnzeige = new Anzeige();

        if (anzeige.getHelfer() != null) {
            newAnzeige.setType(ErstellerStatus.HELFER);
        } else {
            newAnzeige.setType(ErstellerStatus.FLUECHTLING);
        }

        newAnzeige.setDatum(LocalDateTime.now());
        // todo bilder als link?
        newAnzeige.setDescription(anzeige.getDescription());
        newAnzeige.setStatus(anzeige.getStatus());

        // todo FK
        return anzeigeRepository.save(newAnzeige);
    }

    public Anzeige updateAnzeige(Anzeige anzeige) {

        Anzeige anzeigeToUpdate = getAnzeige(anzeige.getId());

        if (anzeige.getType() != null)
            anzeige.setType(anzeige.getType());
        if (anzeige.getDatum() != null) //todo bilder
            anzeige.setDatum(anzeige.getDatum());
        if (anzeige.getBilds() != null)
            anzeige.setBilds(anzeige.getBilds());
        if (anzeige.getDescription() != null)
            anzeige.setDescription(anzeige.getDescription());

        // todo FK

        return anzeigeRepository.save(anzeigeToUpdate);
    }

    public ResponseEntity<?> deleteAnzeige(UUID id) {
        Anzeige anzeigeToDelete = getAnzeige(id);

        anzeigeRepository.deleteById(anzeigeToDelete.getId());
        // log.info("successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }


}
