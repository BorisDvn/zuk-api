package com.thb.zukapi.dtos.admin;

import java.util.List;
import java.util.stream.Collectors;

import com.thb.zukapi.models.Admin;

public class Admin2AdminReadListTO {

	public static AdminReadListTO apply(Admin in) {
		AdminReadListTO out = new AdminReadListTO();

		out.setId(in.getId());
		out.setLastname(in.getLastname());
		out.setFirstname(in.getFirstname());

		if (in.getNationality() != null)
			out.setNationality(in.getNationality());

		out.setDob(in.getDob());
		out.setPhone(in.getPhone());
		out.setEmail(in.getEmail());
		out.setAddress(in.getAddress());
		out.setGender(in.getGender());

		return out;
	}

	public static List<AdminReadListTO> apply(List<Admin> admnis) {
		return admnis.stream().map(Admin2AdminReadListTO::apply).collect(Collectors.toList());
	}

}
