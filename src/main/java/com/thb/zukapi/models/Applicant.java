package com.thb.zukapi.models;

import java.util.UUID;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Applicant extends Auditable<String> {
	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	UUID id;

	@NotBlank
	String details; // The content of the form ??

	@ElementCollection
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "announcement_id", referencedColumnName = "id", nullable = false)
	Announcement announcement;

	@Enumerated(EnumType.STRING)
	ContactStatus status;

	// The following information will be asked, when the user is not connected ->
	// from frontend

	String email;

	String phone;

	String name;

	// this will be use for the push notification purpose. (Later) -> from frontend

	String deviceId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "seeker_id", referencedColumnName = "id")
	Seeker seeker;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "helper_id", referencedColumnName = "id")
	Helper helper;
}
