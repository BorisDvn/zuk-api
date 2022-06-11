package com.thb.zukapi.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sun.istack.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.UUID;

// todo: this class will not be persisted
@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class Person extends AuditingCommonEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    protected UUID id;

    String lastname;

    // vorname
    String firstname;

    String nationality;

    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "YYYY-MM-DD")
    LocalDate dob; // date of birth

    @NotBlank
    String phone;

    @Email
    @Column(unique = true)
    String email; // As username

    @NotBlank
    String address;

    @Size(min = 8)
    String password;

    @NotBlank
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Gender gender;

    @NotBlank
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    RoleType role;
}
