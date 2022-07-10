package com.thb.zukapi.category;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import com.thb.zukapi.ItBase;
import com.thb.zukapi.dtos.category.CategoryWriteTO;
import com.thb.zukapi.dtos.files.FileTO;
import com.thb.zukapi.models.Category;
import com.thb.zukapi.models.File;

import io.restassured.http.ContentType;

public class CategoryIT extends ItBase {

    Category category, category1;
            
    File file;
    
    FileTO fileTO;
    

    @BeforeEach
    public void setup() {
        super.setup();

        file = buildFile();
        file = fileRepository.save(file);
        
        category = buildCategory(file);
        category = categoryRepository.save(category);

        category1 = buildCategory(file);
        category1 = categoryRepository.save(category1);
        
        fileTO = buildFileTO();
        
        when(fileUpload.uploadToFileService(any(), any())).thenReturn(fileTO);

    }

    @AfterEach
    public void cleanup() {
        super.cleanup();
    }


//    @Test
//    public void createCategory() {
//    	CategoryWriteTO toCreate = buildCategoryWriteTO();
//    	
//        UUID id = UUID.fromString(
//                given()
//                        .contentType(ContentType.JSON)
//                        .multiPart("category", toCreate,"application/json")
//                        .multiPart("file", new MockMultipartFile("foo", "foo.txt", MediaType.TEXT_PLAIN_VALUE,
//                        	      "Hello World".getBytes()))
////                        .body()
//                        .log().body()
//                        .post("zuk-api/v1/category")
//                        .then()
//                        .log().body()
//                        .statusCode(200)
//                        .extract().body().path("id"));
//
//        Category category = categoryRepository.findById(id).get();
//
//        assertThat(toCreate.getName(), is(category.getName()));
//    }
//    
//    @Test
//    public void getCategory() {
//
//        UUID id = UUID.fromString(
//                given()
//                        .contentType(ContentType.JSON)
//                        .log().body()
//                        .get("zuk-api/v1/category/"+category.getId())
//                        .then()
//                        .log().body()
//                        .statusCode(200)
//                        .extract().body().path("id"));
//
//        Category category_ = categoryRepository.findById(id).get();
//
//        assertThat(category_.getName(), is(category.getName()));
//    }
//    
//    @Test
//    public void deleteCategory() {
//
//        given()
//                .contentType(ContentType.JSON)
//                .log().body()
//                .get("zuk-api/v1/category/"+category.getId())
//                .then()
//                .log().body()
//                .statusCode(200);
//
//        Optional<Category> category_ = categoryRepository.findById(category.getId());
//
//        assertThat(category_.isPresent(), is(false));
//    }
}
