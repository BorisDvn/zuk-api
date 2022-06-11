package com.thb.zukapi.models;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Announcement extends AuditingCommonEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    UUID id;

    @NotBlank
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    AnnouncementStatus status; // standby as default value

    private byte[] images;

    @NotBlank
    @NonNull
    String description;

    @NotBlank
    @NonNull
    String title;

    // todo FK
}
