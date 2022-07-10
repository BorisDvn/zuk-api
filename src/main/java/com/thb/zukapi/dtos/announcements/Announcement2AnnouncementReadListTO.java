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

		return out;
	}

	public static List<AnnouncementReadListTO> apply(List<Announcement> categories) {
		return categories.stream().map(Announcement2AnnouncementReadListTO::apply).collect(Collectors.toList());
	}
}
