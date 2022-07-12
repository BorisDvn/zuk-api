package com.thb.zukapi.dtos.applicants;

import java.util.Date;
import java.util.UUID;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

import com.thb.zukapi.models.ContactStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApplicantReadTO {

	UUID id;

	String details;

	@NotBlank
	UUID announcementId;

	@Enumerated(EnumType.STRING)
	ContactStatus status;

	// The following information will be asked, when the user is not connected ->
	// from frontend

	String email;

	String phone;

	String name;

	// this will be use for the push notification purpose. (Later) -> from frontend

	String deviceId;	

	UUID creatorId;
	
	String createdBy;

	Date createdDate;

	String lastModifiedBy;

	Date lastModifiedDate;
}
