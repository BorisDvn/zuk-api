package com.thb.zukapi.services;

import com.thb.zukapi.exception.ApiRequestException;
import com.thb.zukapi.models.Category;
import com.thb.zukapi.repositories.KategorieRepository;
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
    @Autowired
    private KategorieRepository kategorieRepository;

    public Category getKategorie(UUID id) {
        return kategorieRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Cannot find Category with id: " + id));
    }

    public List<Category> getAll(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Category> pagedResult = kategorieRepository.findAll(paging);

        return pagedResult.getContent();
    }

    public Category addKategorie(Category kategorie) {

        Category newKategorie = new Category();
        newKategorie.setName(kategorie.getName());

        // todo FK
        return kategorieRepository.save(newKategorie);
    }

    public Category updateKategorie(Category kategorie) {

        Category kategorieToUpdate = getKategorie(kategorie.getId());

        if (kategorie.getName() != null)
            kategorieToUpdate.setName(kategorie.getName());

        // todo FK

        return kategorieRepository.save(kategorieToUpdate);
    }

    public ResponseEntity<?> deleteKategorie(UUID id) {
        Category kategorieToDelete = getKategorie(id);

        kategorieRepository.deleteById(kategorieToDelete.getId());
        // log.info("successfully deleted");

        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }
}
