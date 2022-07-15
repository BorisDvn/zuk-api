package com.thb.zukapi.dtos.admin;

import java.util.List;
import java.util.stream.Collectors;

import com.thb.zukapi.models.Admin;

public class Admin2AdminReadTO {

	public static AdminReadTO apply(Admin in) {
		AdminReadTO out = new AdminReadTO();

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
		out.setAddress(in.getAddress());
		out.setGender(in.getGender());
		out.setUserId(in.getUser().getId());

		if(in.getUser() != null)
			out.setRoles(in.getUser().getRoles().stream().collect(Collectors.toList()));

		return out;
	}

	public static List<AdminReadTO> apply(List<Admin> helpers) {
		return helpers.stream().map(Admin2AdminReadTO::apply).collect(Collectors.toList());
	}

}
