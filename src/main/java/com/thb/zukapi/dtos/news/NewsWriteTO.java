package com.thb.zukapi.dtos.news;

import java.util.UUID;

import javax.persistence.Lob;
import javax.validation.constraints.NotBlank;

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
public class NewsWriteTO {

	UUID id;

	@NotBlank
	String title;

	@Lob
	@NotBlank
	String description;

}