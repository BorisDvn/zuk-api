package com.thb.zukapi.dtos.manager;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.thb.zukapi.dtos.person.PersonWriteTO;
import com.thb.zukapi.models.Role;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ManagerReadTO extends PersonWriteTO {
    
	UUID userId;
    
    String createdBy;

	Date createdDate;

	String lastModifiedBy;

	Date lastModifiedDate;
	
	List<Role> roles;

}
