package com.thb.zukapi.dtos.admin;

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
public class AdminReadListTO extends PersonWriteTO {
    
}
