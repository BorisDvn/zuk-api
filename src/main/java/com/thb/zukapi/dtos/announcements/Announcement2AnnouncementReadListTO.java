package com.thb.zukapi.dtos.announcements;

import java.util.List;
import java.util.stream.Collectors;

import com.thb.zukapi.models.Announcement;

public class Announcement2AnnouncementReadListTO {
	public static AnnouncementReadListTO apply(Announcement in) {
		AnnouncementReadListTO out = new AnnouncementReadListTO();

		out.setId(in.getId());
		out.setTitle(in.getTitle());
		out.setStatus(in.getStatus());
		out.setType(in.getType());

		if(in.getImages() != null && in.getImages().size() > 0)
			out.setImageLink(in.getImages().get(0).getFileLink());

		out.setCategoryId(in.getCategory().getId());

		if (in.getAdmin() != null) {
			out.setCreatorId(in.getAdmin().getId());
		}

		if (in.getManager() != null) {
			out.setCreatorId(in.getManager().getId());
		}

		if (in.getSeeker() != null) {
			out.setCreatorId(in.getSeeker().getId());
		}

		if (in.getHelper() != null) {
			out.setCreatorId(in.getHelper().getId());
		}

		return out;
	}

	public static List<AnnouncementReadListTO> apply(List<Announcement> categories) {
		return categories.stream().map(Announcement2AnnouncementReadListTO::apply).collect(Collectors.toList());
	}
}
