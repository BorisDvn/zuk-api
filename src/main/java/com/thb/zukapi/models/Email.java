package com.thb.zukapi.models;

import lombok.*;
import lombok.experimental.FieldDefaults;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Email {

    String[] to;

    String from;

    String subject;

    String message;

    boolean reply;
}
