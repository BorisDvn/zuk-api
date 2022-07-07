package com.thb.zukapi.models;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "announcement")
public class Announcement {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    UUID id;

    @NotBlank
    String title;

    LocalDateTime announcementDate;

	@ElementCollection
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "image_id", referencedColumnName = "id")
	List<File> images;

    @Lob
    @NotBlank
    String description;

    @Enumerated(EnumType.STRING)
    AnnouncementStatus status; // standby as default value

    @JsonIdentityReference(alwaysAsId = true)
    @ManyToOne(fetch = FetchType.LAZY)
    Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    Helper helper;

    @ManyToOne(fetch = FetchType.LAZY)
    Seeker seeker;
}
