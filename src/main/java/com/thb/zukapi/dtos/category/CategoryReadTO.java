package com.thb.zukapi.dtos.category;

import java.util.List;
import java.util.UUID;

import javax.validation.constraints.NotBlank;

import com.thb.zukapi.dtos.announcements.AnnouncementReadListTO;
import com.thb.zukapi.models.File;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CategoryReadTO {

    UUID id;

    @NotBlank
    File cover; // cover pic

    @NotBlank
    String name;

    List<AnnouncementReadListTO> announcements;
}
