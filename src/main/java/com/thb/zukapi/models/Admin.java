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
    String role;

    @Size(min = 10)
    String password;

    @Builder
    public Admin(UUID id, String nachname, String vorname, String staatsangeroerigkeit,
                 LocalDate geburtsdatum, String telefonnummer, String email,
                 String adresse, String role, String password) {
        super(id, nachname, vorname, staatsangeroerigkeit,
                geburtsdatum, telefonnummer, email,
                adresse);

        this.role = role;
        this.password = password;
    }
}
