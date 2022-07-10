package com.thb.zukapi.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

@Entity
@Setter
@Getter
@NoArgsConstructor
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Seeker extends Person {

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	User user;

	@OneToMany(mappedBy = "seeker", fetch = FetchType.EAGER)
	List<Announcement> announcements = new ArrayList<Announcement>();
}

