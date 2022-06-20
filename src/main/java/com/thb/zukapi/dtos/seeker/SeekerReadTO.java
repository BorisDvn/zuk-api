package com.thb.zukapi.dtos.seeker;

import com.thb.zukapi.dtos.person.PersonReadTO;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeekerReadTO extends PersonReadTO {
}
