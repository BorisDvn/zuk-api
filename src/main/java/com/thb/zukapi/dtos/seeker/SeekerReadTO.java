package com.thb.zukapi.dtos.seeker;

import com.thb.zukapi.dtos.person.PersonWriteTO;
import com.thb.zukapi.models.Announcement;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
//@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeekerReadTO extends PersonWriteTO {

    List<Announcement> announcements; // TODO ReadListTO
}
