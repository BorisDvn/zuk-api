package com.thb.zukapi.dtos.announcements;

import java.util.UUID;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

import com.thb.zukapi.models.AnnouncementStatus;

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
public class AnnouncementReadListTO {

	UUID id;

	@NotBlank
	String title;

	@Enumerated(EnumType.STRING)
	AnnouncementStatus status;
	
	String imageLink;

}
