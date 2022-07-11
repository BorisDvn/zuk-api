package com.thb.zukapi.models;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
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
@Table(name = "announcement")
public class Announcement extends Auditable<String> {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    UUID id;

    @NotBlank
    String title;

    @ElementCollection
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_announcement_id", referencedColumnName = "id")
    List<File> images;

    @Lob
    @NotBlank
    String description;

    @Enumerated(EnumType.STRING)
    AnnouncementStatus status; // standby as default value
    
    @Enumerated(EnumType.STRING)
    AnnouncementStype type;

    //for not registered users
    @Email
    String email;

    String tel;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "helper_id", referencedColumnName = "id")
	Helper helper;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seeker_id", referencedColumnName = "id")
	Seeker seeker;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "admin_id", referencedColumnName = "id")
	Admin admin;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "manager_id", referencedColumnName = "id")
	Manager manager;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", referencedColumnName = "id", nullable = false)
    Category category;

	@ElementCollection
	@OneToMany(mappedBy = "announcement", fetch = FetchType.LAZY)
	List<Applicant> applicants;
}
