package com.thb.zukapi.dtos.manager;

import java.util.List;
import java.util.stream.Collectors;

import com.thb.zukapi.models.Manager;

public class Manager2ManagerReadListTO {

	public static ManagerReadListTO apply(Manager in) {
		ManagerReadListTO out = new ManagerReadListTO();

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

	public static List<ManagerReadListTO> apply(List<Manager> managers) {
		return managers.stream().map(Manager2ManagerReadListTO::apply).collect(Collectors.toList());
	}

}
