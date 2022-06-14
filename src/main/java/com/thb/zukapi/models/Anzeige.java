package com.thb.zukapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Anzeige {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    UUID id;

    ErstellerStatus type;

    LocalDateTime datum;

    private byte[] bilds; // todo: bilder

    String description;

    AnzeigeStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    Helfer helfer;
}
