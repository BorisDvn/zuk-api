package com.thb.zukapi.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.thb.zukapi.dtos.files.FileTO;
import com.thb.zukapi.dtos.news.NewsWriteTO;
import com.thb.zukapi.dtos.news.SearchTO;
import com.thb.zukapi.exception.ApiRequestException;
import com.thb.zukapi.models.File;
import com.thb.zukapi.models.News;
import com.thb.zukapi.repositories.FileRepository;
import com.thb.zukapi.repositories.NewsRepository;
import com.thb.zukapi.utils.FileUpload;

@EnableScheduling
@Service
public class NewsService {

	private final Logger logger = LoggerFactory.getLogger(NewsService.class);

	@Autowired
	private FileRepository fileRepo;

	@Autowired
	private FileUpload fileUpload;

	@Autowired
	private NewsRepository newsRepository;

	@Value("${newsApi.authorization}")
	private String authorization;

	@Value("${newsApi.url}")
	private String url;

	public News getNews(UUID id) {
		return findNews(id);
	}

	public List<News> getAll(Integer pageNo, Integer pageSize, String sortBy) {

		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
		Page<News> pagedResult = newsRepository.findAll(paging);

		return pagedResult.getContent();
	}

	public News addNews(NewsWriteTO news, List<MultipartFile> files) {

		List<File> uploadedFiles = new ArrayList<>();

		if (files != null && files.size() > 0) {
			for (MultipartFile file : files) {
				FileTO response = fileUpload.uploadToFileService(file, FileUpload.uploadFolder);

				File cover = new File();
				cover.setFileLink(response.getFileLink());
				cover.setName(response.getFilename());
				cover = fileRepo.save(cover);
				uploadedFiles.add(cover);
			}
		}

		News newNews = new News();

		newNews.setTitle(news.getTitle());
		newNews.setImages(uploadedFiles);
		newNews.setDescription(news.getDescription());

		return newsRepository.save(newNews);
	}

	public News updateNews(NewsWriteTO news) {

		News newsToUpdate = getNews(news.getId());

		if (news.getTitle() != null)
			newsToUpdate.setTitle(news.getTitle());
		if (news.getDescription() != null)
			newsToUpdate.setDescription(news.getDescription());

		return newsRepository.save(newsToUpdate);
	}

	// manage images in another endpoint and not in updateFunction to simplefy the
	// complexity
	public News addImageNews(UUID newsId, MultipartFile file) {

		News newsToUpdate = findNews(newsId);

		if (fileRepo.findByName(file.getOriginalFilename()).isPresent()) {
			newsToUpdate.getImages().add(fileRepo.findByName(file.getOriginalFilename()).get());
		} else {
			FileTO response = fileUpload.uploadToFileService(file, FileUpload.uploadFolder);

			File image = new File();
			image.setFileLink(response.getFileLink());
			image.setName(response.getFilename());
			image = fileRepo.save(image);

			newsToUpdate.getImages().add(image);
		}
		return newsRepository.save(newsToUpdate);
	}

	// manage images in another endpoint and not in updateFunction to simplefy the
	// complexity
	public News removeImageNews(UUID newsId, MultipartFile file) {

		News newsToUpdate = findNews(newsId);

		if (fileRepo.findByName(file.getOriginalFilename()).isPresent()) {
			newsToUpdate.getImages().remove(fileRepo.findByName(file.getOriginalFilename()).get());
		} else {
			throw new ApiRequestException(
					String.format("the file with name %s does not exist", file.getOriginalFilename()));
		}
		return newsRepository.save(newsToUpdate);
	}

	public ResponseEntity<String> deleteNewsById(UUID id) {
		News newsToDelete = getNews(id);

		newsRepository.deleteById(newsToDelete.getId());

		logger.info("News successfully deleted");
		return new ResponseEntity<>("News successfully deleted", HttpStatus.OK);
	}

	public News findNews(UUID id) {
		return newsRepository.findById(id)
				.orElseThrow(() -> new ApiRequestException("Cannot find News with id: " + id));
	}

	// @Scheduled(cron = "0 */2 * * * *") // every 2 min -> for test
	@Scheduled(cron = "0 0 7 */4 * *") // At 07:00 AM, every 4 days
	public void cronJobNews() {
		// build Header
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", authorization);
		headers.add("cache-control", "no-cache");
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

		// prepare the request
		HttpEntity request = new HttpEntity(headers);

		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<SearchTO> response = null;
		try {
			// send request
			response = restTemplate.exchange(url, HttpMethod.GET, request, SearchTO.class);
		} catch (Exception e) {
			throw new ApiRequestException(e.getMessage());
		}
		SearchTO responseRequest = response.getBody();
		if (responseRequest.getNews().size() > 0) {
			for (News news : responseRequest.getNews()) {
				// we don't save already existing news
				if (!newsRepository.findByTitle(news.getTitle()).isPresent()) {
					// get only news with description
					if (news.getDescription() != null && !news.getDescription().isEmpty()) {
						newsRepository.save(news);
					}
				}
			}
		}
		logger.info("Successfully saved news");

	}
}
