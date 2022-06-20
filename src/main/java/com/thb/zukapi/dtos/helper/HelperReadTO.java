package com.thb.zukapi.dtos.helper;

import com.thb.zukapi.dtos.person.PersonReadTO;
import com.thb.zukapi.models.Announcement;
import com.thb.zukapi.models.HelperType;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HelperReadTO extends PersonReadTO {

    HelperType helperType;

    List<Announcement> announcements; // TODO ReadListTO

}
