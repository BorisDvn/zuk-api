package com.thb.zukapi.services;

import com.thb.zukapi.exception.ApiRequestException;
import com.thb.zukapi.models.Category;
import com.thb.zukapi.repositories.CategoryRepository;
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
public class CategoryService {

    private final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    @Autowired
    private CategoryRepository categoryRepository;

    public Category getCategory(UUID id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ApiRequestException("Cannot find Category with id: " + id));
    }

    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name)
                .orElseThrow(() -> new ApiRequestException("Cannot find Category with name: " + name));
    }

    public List<Category> getAll(Integer pageNo, Integer pageSize, String sortBy) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Category> pagedResult = categoryRepository.findAll(paging);

        return pagedResult.getContent();
    }

    public Category addCategory(Category category) {

        Category newCategory = new Category();

        newCategory.setCover(category.getCover());
        newCategory.setName(category.getName());
        newCategory.setAnnouncements(category.getAnnouncements());

        return categoryRepository.save(newCategory);
    }

    public Category updateCategory(Category category) {

        Category categoryToUpdate = getCategory(category.getId());

        if (category.getName() != null)
            categoryToUpdate.setName(category.getName());
        if (category.getCover() != null)
            categoryToUpdate.setCover(category.getCover());

        return categoryRepository.save(categoryToUpdate);
    }

    public ResponseEntity<String> deleteCategoryById(UUID id) {

        if (getCategory(id) != null) {
            categoryRepository.deleteById(id);
        }

        logger.info("Category successfully deleted");
        return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
    }
}
