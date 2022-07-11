package com.thb.zukapi.dtos.announcements;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import com.thb.zukapi.models.AnnouncementStatus;
import com.thb.zukapi.models.File;
import com.thb.zukapi.models.RoleType;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnnouncementReadTO {

	UUID id;

	@NotBlank
	String title;

	@Lob
	@NotBlank
	String description;

	@Enumerated(EnumType.STRING)
	AnnouncementStatus status;

	@Enumerated(EnumType.STRING)
	RoleType creatorStatus;

	UUID categoryId;

	UUID creatorId;

	List<File> images;
	
	String createdBy;

	Date createdDate;

	String lastModifiedBy;

	Date lastModifiedDate;
}
