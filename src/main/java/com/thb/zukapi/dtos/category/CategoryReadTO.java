package com.thb.zukapi.dtos.category;

import com.thb.zukapi.models.Announcement;
import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryReadTO {

    UUID id;

    @NotBlank
    String cover; // cover pic

    @NotBlank
    String name;

    List<Announcement> announcements; // TODO: announcementsReadList!?
}
