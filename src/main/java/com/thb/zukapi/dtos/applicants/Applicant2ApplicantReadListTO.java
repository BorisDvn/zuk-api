package com.thb.zukapi.dtos.applicants;

import java.util.List;
import java.util.stream.Collectors;

import com.thb.zukapi.models.Applicant;

public class Applicant2ApplicantReadListTO {
	public static ApplicantReadListTO apply(Applicant in) {
		ApplicantReadListTO out = new ApplicantReadListTO();

		out.setId(in.getId());
		out.setDetails(in.getDetails());
		out.setAnnouncementId(in.getAnnouncement().getId());

		if (in.getStatus() != null)
			out.setStatus(in.getStatus());
		if (in.getEmail() != null)
			out.setEmail(in.getEmail());
		if (in.getName() != null)
			out.setName(in.getName());

		return out;
	}

	public static List<ApplicantReadListTO> apply(List<Applicant> categories) {
		return categories.stream().map(Applicant2ApplicantReadListTO::apply).collect(Collectors.toList());
	}
}
