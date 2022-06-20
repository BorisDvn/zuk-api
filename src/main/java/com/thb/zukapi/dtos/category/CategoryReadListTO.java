package com.thb.zukapi.dtos.category;

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
    String name;
}
