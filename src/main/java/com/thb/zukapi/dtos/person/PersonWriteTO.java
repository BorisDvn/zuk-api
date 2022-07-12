package com.thb.zukapi.dtos.person;

import java.time.LocalDate;
import java.util.UUID;

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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PROTECTED)
public class PersonWriteTO {
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
	LocalDate dob; // date of birth

	@NotBlank
	String phone;

	@Email
	@NotBlank
	String email; // As username

	@NotBlank
	String address;

	@Enumerated(EnumType.STRING)
	Gender gender;

	@Size(min = 6)
	String password;

	String role;

	@Enumerated(EnumType.STRING)
	HelperType helperType;
}
