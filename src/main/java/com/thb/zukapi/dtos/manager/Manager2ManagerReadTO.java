package com.thb.zukapi.dtos.manager;

import java.util.List;
import java.util.stream.Collectors;

import com.thb.zukapi.models.Manager;

public class Manager2ManagerReadTO {

	public static ManagerReadTO apply(Manager in) {
		ManagerReadTO out = new ManagerReadTO();

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

		return out;
	}

	public static List<ManagerReadTO> apply(List<Manager> helpers) {
		return helpers.stream().map(Manager2ManagerReadTO::apply).collect(Collectors.toList());
	}

}
