package com.thb.zukapi.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Helfer extends Person {
    String status;
}
