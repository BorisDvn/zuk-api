package com.thb.zukapi.dtos.seeker;

import com.thb.zukapi.dtos.person.PersonReadTO;
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
public class SeekerWriteTO extends PersonReadTO {
}
