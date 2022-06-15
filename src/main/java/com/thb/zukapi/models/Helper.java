package com.thb.zukapi.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
//@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Helper extends Person {
    @NotBlank
    @Enumerated(EnumType.STRING)
    HelperStatus helperStatus;

    @Builder
    public Helper(UUID id, String lastname, String firstname, String nationality,
                  LocalDate dob, String phone, String email,
                  String adresse, Gender gender, HelperStatus helperStatus) {
        super(id, lastname, firstname, nationality,
                dob, phone, email,
                adresse, gender);

        this.helperStatus = helperStatus;

    }

    @OneToMany(mappedBy = "helper", fetch = FetchType.LAZY)
    List<Announcement> anzeigen;
}
