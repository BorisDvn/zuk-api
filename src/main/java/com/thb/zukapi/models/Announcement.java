package com.thb.zukapi.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Announcement {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    UUID id;

    @NotBlank
    String title;

    LocalDateTime datum;

    byte[] bild; // todo: bilder

    @Lob
    @NotBlank
    String description;

    @NotBlank
    @Enumerated(EnumType.STRING)
    AnnouncementStatus status; // standby as default value

    @ManyToOne(fetch = FetchType.LAZY)
    Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    Helper helper;
}
