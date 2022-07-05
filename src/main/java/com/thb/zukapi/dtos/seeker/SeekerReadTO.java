package com.thb.zukapi.dtos.seeker;

import java.util.List;
import java.util.UUID;

import com.thb.zukapi.dtos.person.PersonWriteTO;
import com.thb.zukapi.models.Announcement;

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

    List<Announcement> announcements; // TODO ReadListTO
}
