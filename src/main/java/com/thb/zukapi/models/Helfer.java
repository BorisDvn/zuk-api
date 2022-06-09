package com.thb.zukapi.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
//@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Helfer extends Person {

    @Builder
    public Helfer(UUID id, String nachname, String vorname, String staatsangeroerigkeit,
                  LocalDate geburtsdatum, String telefonnummer, String email,
                  String adresse) {
        super(id, nachname, vorname, staatsangeroerigkeit,
                geburtsdatum, telefonnummer, email,
                adresse);

    }

    @OneToMany(mappedBy = "helfer", fetch = FetchType.LAZY)
    List<Anzeige> anzeigen;
}
