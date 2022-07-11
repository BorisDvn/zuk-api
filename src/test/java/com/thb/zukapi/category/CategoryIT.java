/*package com.thb.zukapi.category;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.thb.zukapi.ItBase;
import com.thb.zukapi.dtos.category.CategoryWriteTO;
import com.thb.zukapi.dtos.files.FileTO;
import com.thb.zukapi.models.Announcement;
import com.thb.zukapi.models.Category;
import com.thb.zukapi.models.File;
import com.thb.zukapi.models.Helper;

import io.restassured.http.ContentType;

public class CategoryIT extends ItBase {

	Category category;

	File file;

	FileTO fileTO;
	
    Announcement announcement, announcement1;
    
    Helper helper, helper1;

    @BeforeEach
    public void setup() {
        super.setup();

        file = buildFile();
        file = fileRepository.save(file);
        
        category = buildCategory(file);
        category = categoryRepository.save(category);
        
        helper = buildHelper(user);
        helper = helperRepository.save(helper);
        
        announcement = buildAnnouncement(helper, category);
        announcement = announcementRepository.save(announcement);

        announcement1 = buildAnnouncement(helper, category);
        announcement1 = announcementRepository.save(announcement1);

		fileTO = buildFileTO();

		when(fileUpload.uploadToFileService(any(), any())).thenReturn(fileTO);

	}

	@AfterEach
	public void cleanup() {
		super.cleanup();
	}

	// TODO find the right  way to moack multipartfile
//	@Test
//	public void createCategory() {
//		CategoryWriteTO toCreate = buildCategoryWriteTO();
//		
//		MultipartFile fichier = new MockMultipartFile("fileThatDoesNotExists.txt",
//	            "fileThatDoesNotExists.txt",
//	            "text/plain",
//	            "This is a dummy file content".getBytes(StandardCharsets.UTF_8));
//
//		UUID id = UUID.fromString(
//				given()
//						.contentType(ContentType.JSON)
//						.multiPart("category", toCreate, "application/json")
//						.multiPart("file", fichier)
////                        .body()
//						.log()
//						.body()
//						.post("zuk-api/v1/category")
//						.then()
//						.log()
//						.body()
//						.statusCode(200)
//						.extract()
//						.body().path("id"));
//
//		Category category = categoryRepository.findById(id).get();
//
//		assertThat(toCreate.getName(), is(category.getName()));
//	}

	@Test
	public void getCategory() {

		UUID id = UUID.fromString(
				given()
						.contentType(ContentType.JSON)
						.log().body()
						.get("zuk-api/v1/category/" + category.getId())
						.then()
						.log().body()
						.statusCode(200)
						.extract()
						.body().path("id"));

		Category category_ = categoryRepository.findById(id).get();

		assertThat(category_.getName(), is(category.getName()));
	}
	
	@Test
	public void getCategoryByName() {

		UUID id = UUID.fromString(
				given()
						.contentType(ContentType.JSON)
						.log().body()
						.get("zuk-api/v1/category/name/" + category.getName())
						.then()
						.log().body()
						.statusCode(200)
						.extract()
						.body().path("id"));

		Category category_ = categoryRepository.findById(id).get();

		assertThat(category_.getName(), is(category.getName()));
	}

	@Test
    public void getAllCategories() {

                given()
                        .contentType(ContentType.JSON)
                        .log().body()
                        .get("zuk-api/v1/category")
                        .then()
                        .log().body()
                        .statusCode(200)
                        .body("id", containsInAnyOrder(category.getId().toString()));
    }
	
	@Test
	public void updateCategory() {
		
		String newName = UUID.randomUUID().toString();
				
		CategoryWriteTO toUpdate = buildCategoryWriteTO();
		toUpdate.setId(category.getId());
		toUpdate.setName(newName);

		UUID id = UUID.fromString(
				given()
						.contentType(ContentType.JSON)
						.body(toUpdate)
						.put("zuk-api/v1/category")
						.then()
						.log().body()
						.statusCode(200)
						.extract()
						.body().path("id"));

		Category category_ = categoryRepository.findById(id).get();

		assertThat(category_.getName(), is(newName));
	}

	@Test
	public void deleteCategory() {

		given()
				.contentType(ContentType.JSON)
				.log().body()
				.delete("zuk-api/v1/category/" + category.getId())
				.then()
				.log()
				.body()
				.statusCode(200);

		Optional<Category> category_ = categoryRepository.findById(category.getId());

		assertThat(category_.isPresent(), is(false));
	}
}
*/