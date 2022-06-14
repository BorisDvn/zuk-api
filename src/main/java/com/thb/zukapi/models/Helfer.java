package com.thb.zukapi.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
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
    @Enumerated(EnumType.STRING)
    HelferStatus helferStatus;

    @Builder
    public Helfer(UUID id, String lastname, String firstname, String nationality,
                  LocalDate dob, String phone, String email,
                  String adresse, Gender gender, String password, String role) {
        super(id, lastname, firstname, nationality,
                dob, phone, email,
                adresse, gender);

        this.helferStatus = helferStatus;

    }

    @OneToMany(mappedBy = "helfer", fetch = FetchType.LAZY)
    List<Anzeige> anzeigen;
}
