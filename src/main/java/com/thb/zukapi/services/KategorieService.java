package com.thb.zukapi.services;

import com.thb.zukapi.exception.ApiRequestException;
import com.thb.zukapi.models.Kategorie;
import com.thb.zukapi.repositories.KategorieRepository;
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
public class KategorieService {

    private final Logger logger = LoggerFactory.getLogger(KategorieService.class);

    @Autowired
    private KategorieRepository kategorieRepository;

    public Kategorie getKategorie(UUID id) {
        return kategorieRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Cannot find Kategorie with id: " + id));
    }

    public List<Kategorie> getAll(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Kategorie> pagedResult = kategorieRepository.findAll(paging);

        return pagedResult.getContent();
    }

    public Kategorie addKategorie(Kategorie kategorie) {

        Kategorie newKategorie = new Kategorie();
        newKategorie.setName(kategorie.getName());

        // todo FK
        return kategorieRepository.save(newKategorie);
    }

    public Kategorie updateKategorie(Kategorie kategorie) {

        Kategorie kategorieToUpdate = getKategorie(kategorie.getId());

        if (kategorie.getName() != null)
            kategorieToUpdate.setName(kategorie.getName());

        // todo FK

        return kategorieRepository.save(kategorieToUpdate);
    }

    public ResponseEntity<?> deleteKategorie(UUID id) {
        Kategorie kategorieToDelete = getKategorie(id);

        kategorieRepository.deleteById(kategorieToDelete.getId());
        // log.info("successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }
}
