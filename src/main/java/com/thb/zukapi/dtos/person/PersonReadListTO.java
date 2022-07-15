package com.thb.zukapi.dtos.person;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.thb.zukapi.models.Gender;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PROTECTED)
public class PersonReadListTO {

    UUID id;

    @Size(min = 2)
    @NotBlank
    String lastname;

    @Size(min = 2)
    @NotBlank
    String firstname;

    String nationality;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate dob; // date of birth

    @NotBlank
    String phone;

    @Email
    @NotBlank
    String email; // As username

    @NotBlank
    String adresse;

    @Enumerated(EnumType.STRING)
    Gender gender;

}
