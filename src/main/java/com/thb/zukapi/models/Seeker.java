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
public class Seeker extends Person {

    @Builder
    public Seeker(UUID id, String lastname, String firstname, String nationality,
                  LocalDate dob, String phone, String email,
                  String adresse, Gender gender, String password, RoleType role) {
        super(id, lastname, firstname, nationality,
                dob, phone, email,
                adresse, gender, password, role);

    }

    @OneToMany(mappedBy = "seeker", fetch = FetchType.LAZY)
    List<Announcement> announcements;
}
