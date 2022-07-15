package com.thb.zukapi.dtos.helper;

import java.util.List;
import java.util.stream.Collectors;

import com.thb.zukapi.models.Helper;

public class Helper2HelperReadListTO {

    public static HelperReadListTO apply(Helper in) {
        HelperReadListTO out = new HelperReadListTO();

        out.setId(in.getId());
        out.setLastname(in.getLastname());
        out.setFirstname(in.getFirstname());

        if (in.getNationality() != null)
            out.setNationality(in.getNationality());

        out.setDob(in.getDob());
        out.setPhone(in.getPhone());
        out.setEmail(in.getEmail());
        out.setAdresse(in.getAddress());
        out.setGender(in.getGender());
        out.setHelperType(in.getHelperType());

        return out;
    }

    public static List<HelperReadListTO> apply(List<Helper> helpers) {
        return helpers.stream().map(Helper2HelperReadListTO::apply).collect(Collectors.toList());
    }
}
