package com.thb.zukapi.services;

import com.thb.zukapi.exception.ApiRequestException;
import com.thb.zukapi.models.Anzeige;
import com.thb.zukapi.models.Information;
import com.thb.zukapi.repositories.InformationRepository;
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
public class InformationService {

    private final Logger logger = LoggerFactory.getLogger(InformationService.class);

    @Autowired
    private InformationRepository informationRepository;

    public Information getInformation(UUID id) {
        return informationRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Cannot find Information with id: " + id));
    }

    public List<Information> getAll(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Information> pagedResult = informationRepository.findAll(paging);

        return pagedResult.getContent();
    }

    public Information addInformation(Information information) {

        Information newInformation = new Information();

        newInformation.setTitle(information.getTitle());
        newInformation.setNachricht(information.getNachricht());
        newInformation.setDatum(information.getDatum());

        return informationRepository.save(newInformation);
    }

    public Information updateInformation(Information information) {

        Information informationToUpdate = getInformation(information.getId());

        if (information.getTitle() != null)
            information.setTitle(information.getTitle());
        if (information.getNachricht() != null)
            information.setNachricht(information.getNachricht());
        if (information.getDatum() != null)
            information.setDatum(information.getDatum());

        return informationRepository.save(informationToUpdate);
    }

    public ResponseEntity<?> deleteInformation(UUID id) {
        Information informationToDelete = getInformation(id);

        informationRepository.deleteById(informationToDelete.getId());
        // log.info("successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }
}
