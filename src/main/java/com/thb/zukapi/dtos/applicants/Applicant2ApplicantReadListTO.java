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

		return out;
	}

	public static List<ApplicantReadListTO> apply(List<Applicant> categories) {
		return categories.stream().map(Applicant2ApplicantReadListTO::apply).collect(Collectors.toList());
	}
}
