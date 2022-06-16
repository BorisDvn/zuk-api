package com.thb.zukapi.dtos.helper;

import java.util.List;
import java.util.stream.Collectors;

import com.thb.zukapi.models.Helper;

public class Helper2HelperReadTO {

	public static HelperReadTO apply(Helper in) {
		HelperReadTO out = new HelperReadTO();

		out.setId(in.getId());
		out.setLastname(in.getLastname());
		out.setFirstname(in.getFirstname());

		if (in.getNationality() != null)
			out.setNationality(in.getNationality());

		out.setDob(in.getDob());
		out.setPhone(in.getPhone());
		out.setEmail(in.getEmail());
		out.setAdresse(in.getAdresse());
		out.setGender(in.getGender());

		if (in.getUser().getPassword() != null)
			out.setPassword(in.getUser().getPassword());

		out.setRole(in.getUser().getRoles().iterator().next().getName().name());

		if (in.getAnnouncements() != null)
			out.setAnnouncements(in.getAnnouncements());

		out.setHelperType(in.getHelperType());

		return out;
	}

	public static List<HelperReadTO> apply(List<Helper> helpers) {
		return helpers.stream().map(Helper2HelperReadTO::apply).collect(Collectors.toList());
	}

}
