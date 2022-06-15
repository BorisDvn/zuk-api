package com.thb.zukapi.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Person {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
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
    @Column(unique = true)
    String email; // As username

    @NotBlank
    String adresse;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Gender gender;

    @Size(min = 10)
    String password;

    @NotBlank
    @Enumerated(EnumType.STRING)
    RoleType role;
}
