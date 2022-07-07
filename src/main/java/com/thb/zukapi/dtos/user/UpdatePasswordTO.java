package com.thb.zukapi.dtos.user;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdatePasswordTO {
	
	UUID id;
	
	String oldPassword;
	
	String newPassword;

}
