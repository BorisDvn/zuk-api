package com.thb.zukapi.dtos.seeker;

import java.util.List;
import java.util.stream.Collectors;

import com.thb.zukapi.models.Seeker;

public class Seeker2SeekerReadTO {

	public static SeekerReadTO apply(Seeker in) {
		SeekerReadTO out = new SeekerReadTO();

		out.setId(in.getId());
		out.setLastname(in.getLastname());
		out.setFirstname(in.getFirstname());
		out.setCreatedBy(in.getCreatedBy());
		out.setCreatedDate(in.getCreatedDate());
		out.setLastModifiedBy(in.getLastModifiedBy());
		out.setLastModifiedDate(in.getLastModifiedDate());

		if (in.getNationality() != null)
			out.setNationality(in.getNationality());

		out.setDob(in.getDob());
		out.setPhone(in.getPhone());
		out.setEmail(in.getEmail());
		out.setAdresse(in.getAdresse());
		out.setGender(in.getGender());
		out.setUserId(in.getUser().getId());

		return out;
	}

	public static List<SeekerReadTO> apply(List<Seeker> seekers) {
		return seekers.stream().map(Seeker2SeekerReadTO::apply).collect(Collectors.toList());
	}

}
