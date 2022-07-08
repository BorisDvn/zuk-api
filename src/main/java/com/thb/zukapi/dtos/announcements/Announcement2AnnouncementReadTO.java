package com.thb.zukapi.dtos.announcements;

import com.thb.zukapi.models.Announcement;
import com.thb.zukapi.models.RoleType;

import java.util.List;
import java.util.stream.Collectors;

public class Announcement2AnnouncementReadTO {
	public static AnnouncementReadTO apply(Announcement in) {
		AnnouncementReadTO out = new AnnouncementReadTO();

		out.setId(in.getId());
		out.setTitle(in.getTitle());
		out.setDescription(in.getDescription());
		out.setStatus(in.getStatus());
		out.setCategoryId(in.getCategory().getId());
		out.setImages(in.getImages());

		if (in.getAdmin() != null) {
			out.setCreatorId(in.getAdmin().getId());
			out.setCreatorStatus(RoleType.ADMIN);
		}

		if (in.getManager() != null) {
			out.setCreatorId(in.getManager().getId());
			out.setCreatorStatus(RoleType.MANAGER);
		}

		if (in.getSeeker() != null) {
			out.setCreatorId(in.getSeeker().getId());
			out.setCreatorStatus(RoleType.SEEKER);
		}

		if (in.getHelper() != null) {
			out.setCreatorId(in.getHelper().getId());
			out.setCreatorStatus(RoleType.HELPER);
		}

		return out;
	}

	public static List<AnnouncementReadTO> apply(List<Announcement> categories) {
		return categories.stream().map(Announcement2AnnouncementReadTO::apply).collect(Collectors.toList());
	}
}
