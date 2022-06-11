/*
 * Represents the relation Person_Announcement
 */
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
public class Apply extends AuditingCommonEntity {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    protected UUID id;

    @NotBlank
    @NonNull
    String createdBy; // almost save email of the applicant here

    @NotBlank
    @NonNull
    String details; // The content of the form

    @NotBlank
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    Status status;

    // TODO: add reference to announcementUuid and PersonUuid
}
