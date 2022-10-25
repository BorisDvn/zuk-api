package com.thb.zukapi.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.thb.zukapi.dtos.files.FileTO;
import com.thb.zukapi.exception.ApiRequestException;

@Service
public class FileUpload {

	private final Logger logger = LoggerFactory.getLogger(FileUpload.class);
	
	public static final String uploadFolder = "/mux/";

	@Value("${fileStorage.url}")
	private String fileServiceUrl;

	public FileTO uploadToFileService(MultipartFile file, String path) {

		// build Header
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);

		// prepare the request
		MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("file", file.getResource());
		body.add("filePath", path + file.getOriginalFilename());

		HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<FileTO> response = null;
		try {

			// upload the file
			response = restTemplate.postForEntity(fileServiceUrl + "upload", requestEntity, FileTO.class);
		} catch (Exception e) {
			logger.info("Error cannot upload file '{}'. Message : '{}' ", file.getOriginalFilename(), e.getMessage());
			throw new ApiRequestException(e.getMessage());
		}

		logger.info("Uploaded file '{}' to '{}' ", file.getOriginalFilename(), path + file.getOriginalFilename());

		return response.getBody();
	}

}
