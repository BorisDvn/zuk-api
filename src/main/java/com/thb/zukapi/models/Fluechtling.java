package com.thb.zukapi.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
//@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Fluechtling extends Person {

    @Builder
    public Fluechtling(UUID id, String nachname, String vorname, String staatsangeroerigkeit,
                       LocalDate geburtsdatum, String telefonnummer, String email,
                       String adresse) {
        super(id, nachname, vorname, staatsangeroerigkeit,
                geburtsdatum, telefonnummer, email,
                adresse);

    }
}
