package com.thb.zukapi.dtos.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SigninTO {

	@Email
	@NotBlank
	String email;

	@Size(min = 6)
	String password;
	
	String role;

}
