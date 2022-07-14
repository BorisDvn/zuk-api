package com.thb.zukapi.dtos.admin;

import java.util.Date;
import java.util.UUID;

import com.thb.zukapi.dtos.person.PersonWriteTO;

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
public class AdminReadTO extends PersonWriteTO {
    
	UUID userId;
    
    String createdBy;

	Date createdDate;

	String lastModifiedBy;

	Date lastModifiedDate;

}
