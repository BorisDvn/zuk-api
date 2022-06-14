package com.thb.zukapi.services;

import com.thb.zukapi.exception.ApiRequestException;
import com.thb.zukapi.models.Category;
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

    public Category getKategorie(UUID id) {
        return kategorieRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Cannot find Category with id: " + id));
    }

    public List<Category> getAll(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Category> pagedResult = kategorieRepository.findAll(paging);

        return pagedResult.getContent();
    }

    public Category addKategorie(Category category) {

        Category newCategory = new Category();

        newCategory.setTitle(category.getTitle());
        newCategory.setCover(category.getCover());
        newCategory.setAnzeigen(category.getAnzeigen());

        return kategorieRepository.save(newCategory);
    }

    public Category updateKategorie(Category category) {

        Category categoryToUpdate = getKategorie(category.getId());

        if (category.getTitle() != null)
            categoryToUpdate.setTitle(category.getTitle());
        if (category.getCover() != null)
            categoryToUpdate.setCover(category.getCover());

        return kategorieRepository.save(categoryToUpdate);
    }

    public ResponseEntity<String> deleteKategorieById(UUID id) {

        Category categoryToDelete = getKategorie(id);

        kategorieRepository.deleteById(categoryToDelete.getId());

        logger.info("Category successfully deleted");
        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }
}
