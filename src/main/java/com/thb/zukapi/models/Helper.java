package com.thb.zukapi.models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotBlank;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Entity
@Setter
@Getter
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Helper extends Person {

	@NotBlank
	@Enumerated(EnumType.STRING)
	HelperType helperType;

	@OneToMany(mappedBy = "helper", fetch = FetchType.LAZY)
	List<Announcement> anzeigen;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	User user;

}