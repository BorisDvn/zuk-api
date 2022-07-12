package com.thb.zukapi.dtos.announcements;

import java.util.UUID;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.thb.zukapi.models.AnnouncementStatus;
import com.thb.zukapi.models.AnnouncementStype;
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
public class AnnouncementWriteTO {

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
	
    @Enumerated(EnumType.STRING)
    AnnouncementStype type;

	UUID categoryId;

	UUID creatorId; // Admin, Helper, Seeker, Manager

	//for not registered users
	@Email
	String email;

	String tel;
}
