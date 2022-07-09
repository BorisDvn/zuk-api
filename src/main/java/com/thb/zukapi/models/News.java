package com.thb.zukapi.models;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.GenericGenerator;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class News extends Auditable<String> {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "uuid2")
	UUID id;

	// Since Postgres 9.2 increasing the character limit,
	// including going from varchar to text

	@Column(columnDefinition = "text")
	@NotBlank
	String title;

	@ElementCollection
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "image_id", referencedColumnName = "id")
	List<File> images;

	@NotBlank
	@Column(columnDefinition = "text")
	String description;

	@Column(columnDefinition = "text")
	String url;

	@Column(columnDefinition = "text")
	String author;

	@Column(columnDefinition = "text")
	String image;

	String published;

}
