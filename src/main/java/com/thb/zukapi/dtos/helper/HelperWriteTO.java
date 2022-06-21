package com.thb.zukapi.dtos.helper;

import com.thb.zukapi.dtos.person.PersonReadTO;
import com.thb.zukapi.models.HelperType;
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
public class HelperWriteTO extends PersonReadTO {
    HelperType helperType;
}
