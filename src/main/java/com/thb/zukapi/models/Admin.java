package com.thb.zukapi.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
//@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Admin extends Person {
    @Size(min = 10)
    String password;

    String role;

    @Builder
    public Admin(UUID id, String lastname, String firstname, String nationality,
                 LocalDate dob, String phone, String email,
                 String adresse, Gender gender, String password, String role) {
        super(id, lastname, firstname, nationality,
                dob, phone, email,
                adresse, gender);

        this.role = role;
        this.password = password;
    }
}
