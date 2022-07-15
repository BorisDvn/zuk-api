package com.thb.zukapi.dtos.files;

import java.util.UUID;

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
public class FileTO {

	UUID id;

	String filename;

	String remoteName;

	String remoteId;

	Long filesize;

	String path;

	String fileLink;

}
