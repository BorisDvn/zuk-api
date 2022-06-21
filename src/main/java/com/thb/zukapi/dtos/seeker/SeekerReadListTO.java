package com.thb.zukapi.dtos.seeker;

import com.thb.zukapi.dtos.person.PersonReadListTO;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;


@Setter
@Getter
@NoArgsConstructor
//@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SeekerReadListTO extends PersonReadListTO {


}
