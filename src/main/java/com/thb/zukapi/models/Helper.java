package com.thb.zukapi.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@DiscriminatorValue("HELPER")
public class Helper extends Person {
    @NotBlank
    @NonNull
    @Enumerated(EnumType.STRING)
    private PersonType type;
}
