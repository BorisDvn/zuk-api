package com.thb.zukapi.dtos.seeker;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.thb.zukapi.dtos.announcements.AnnouncementReadListTO;
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
//@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeekerReadTO extends PersonWriteTO {
	
	UUID userId;

    List<AnnouncementReadListTO> announcements;
   
    String createdBy;

	Date createdDate;

	String lastModifiedBy;

	Date lastModifiedDate;
}
