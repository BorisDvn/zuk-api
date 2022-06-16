package com.thb.zukapi.transfertobjects.user;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thb.zukapi.models.Gender;
import com.thb.zukapi.models.HelperType;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignupTO {
	
	UUID id;

	@Size(min = 2)
	@NotBlank
	String lastname;

	@Size(min = 2)
	@NotBlank
	String firstname;

	String nationality;

	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	LocalDate dob;

	@NotBlank
	String phone;

	@Email
	@NotBlank
	String email;

	@NotBlank
	String adresse;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	Gender gender;

	@Size(min = 6)
	@NotBlank
	String password;

	String role;

	@Enumerated(EnumType.STRING)
	HelperType helperType;
}
