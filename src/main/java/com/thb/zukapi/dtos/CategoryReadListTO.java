package com.thb.zukapi.dtos;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryReadListTO {

    UUID id;

    @NotBlank
    String cover; // cover pic

    @NotBlank
    private String name;
}
