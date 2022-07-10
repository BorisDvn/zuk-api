package com.thb.zukapi.services;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

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
import org.springframework.web.multipart.MultipartFile;

import com.thb.zukapi.dtos.category.Category2CategoryReadListTO;
import com.thb.zukapi.dtos.category.Category2CategoryReadTO;
import com.thb.zukapi.dtos.category.CategoryReadListTO;
import com.thb.zukapi.dtos.category.CategoryReadTO;
import com.thb.zukapi.dtos.category.CategoryWriteTO;
import com.thb.zukapi.dtos.files.FileTO;
import com.thb.zukapi.exception.ApiRequestException;
import com.thb.zukapi.models.Category;
import com.thb.zukapi.models.File;
import com.thb.zukapi.repositories.CategoryRepository;
import com.thb.zukapi.repositories.FileRepository;
import com.thb.zukapi.utils.FileUpload;

@Service
public class CategoryService {

	@Autowired
	private FileRepository fileRepo;

	@Autowired
	private FileUpload fileUpload;

	private final Logger logger = LoggerFactory.getLogger(CategoryService.class);

	@Autowired
	private CategoryRepository categoryRepository;

	public CategoryReadTO getCategory(UUID id) {
		return Category2CategoryReadTO.apply(findCategory(id));
	}

	public Category getCategoryByName(String name) {
		return categoryRepository.findByName(name)
				.orElseThrow(() -> new ApiRequestException("Cannot find Category with name: " + name));
	}

	public List<CategoryReadListTO> getAll(Integer pageNo, Integer pageSize, String sortBy) {

		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<Category> pagedResult = categoryRepository.findAll(paging);

		return Category2CategoryReadListTO.apply(pagedResult.getContent());
	}

	public Category addCategory(CategoryWriteTO category, MultipartFile file) throws IOException {

		FileTO response = fileUpload.uploadToFileService(file, FileUpload.uploadFolder);

		File cover = new File();
		cover.setFileLink(response.getFileLink());
		cover.setName(response.getFilename());

		cover = fileRepo.save(cover);

		Category newCategory = new Category();

		newCategory.setName(category.getName());
		newCategory.setCover(cover);

		return categoryRepository.save(newCategory);
	}

	public Category updateCategory(Category category) {

		Category categoryToUpdate = findCategory(category.getId());

		if (category.getName() != null)
			categoryToUpdate.setName(category.getName());
		if (category.getCover() != null)
			categoryToUpdate.setCover(category.getCover());

		return categoryRepository.save(categoryToUpdate);
	}

	public Category updateCategoryImage(UUID categoryId, MultipartFile file) {

		Category categoryToUpdate = findCategory(categoryId);

		// if the file already exist just use it
		if (fileRepo.findByName(file.getOriginalFilename()).isPresent()) {
			categoryToUpdate.setCover(fileRepo.findByName(file.getOriginalFilename()).get());
		} else {
			// else upload it
			FileTO response = fileUpload.uploadToFileService(file, FileUpload.uploadFolder);

			File cover = new File();
			cover.setFileLink(response.getFileLink());
			cover.setName(response.getFilename());

			cover = fileRepo.save(cover);
			categoryToUpdate.setCover(cover);
		}

		return categoryRepository.save(categoryToUpdate);
	}

	public ResponseEntity<String> deleteCategoryById(UUID id) {

		if (getCategory(id) != null) {
			categoryRepository.deleteById(id);
		}

		logger.info("Category successfully deleted");
		return new ResponseEntity<>("Successfully deleted", HttpStatus.OK);
	}

	public Category findCategory(UUID id) {
		return categoryRepository.findById(id)
				.orElseThrow(() -> new ApiRequestException("Cannot find Category with id: " + id));
	}
}
