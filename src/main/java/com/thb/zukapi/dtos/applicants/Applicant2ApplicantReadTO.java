package com.thb.zukapi.dtos.applicants;

import java.util.List;
import java.util.stream.Collectors;

import com.thb.zukapi.models.Applicant;

public class Applicant2ApplicantReadTO {
	public static ApplicantReadTO apply(Applicant in) {
		ApplicantReadTO out = new ApplicantReadTO();

		out.setId(in.getId());
		out.setDetails(in.getDetails());
		out.setAnnouncementId(in.getAnnouncement().getId());
		out.setStatus(in.getStatus());

		if(in.getEmail() != null)
			out.setEmail(in.getEmail());
		if(in.getPhone() != null)
			out.setPhone(in.getPhone());
		if(in.getName() != null)
			out.setName(in.getName());

		if(in.getSeeker() != null)
			out.setSeekerId(in.getSeeker().getId());
		if(in.getDeviceId() != null)
			out.setDeviceId(in.getDeviceId());
		
		out.setCreatedBy(in.getCreatedBy());
		out.setCreatedDate(in.getCreatedDate());
		out.setLastModifiedBy(in.getLastModifiedBy());
		out.setLastModifiedDate(in.getLastModifiedDate());

		return out;
	}

	public static List<ApplicantReadTO> apply(List<Applicant> categories) {
		return categories.stream().map(Applicant2ApplicantReadTO::apply).collect(Collectors.toList());
	}
}
