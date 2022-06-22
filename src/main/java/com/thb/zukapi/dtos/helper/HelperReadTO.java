package com.thb.zukapi.dtos.helper;

import com.thb.zukapi.dtos.person.PersonWriteTO;
import com.thb.zukapi.models.Announcement;
import com.thb.zukapi.models.HelperType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HelperReadTO extends PersonWriteTO {

    HelperType helperType;
    
	UUID userId;

    List<Announcement> announcements; // TODO ReadListTO

}
