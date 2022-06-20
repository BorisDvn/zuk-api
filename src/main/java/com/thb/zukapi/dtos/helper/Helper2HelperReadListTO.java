package com.thb.zukapi.dtos.helper;

import com.thb.zukapi.models.Helper;
import com.thb.zukapi.models.Seeker;

import java.util.List;
import java.util.stream.Collectors;

public class Helper2HelperReadListTO {

    public static HelperReadListTO apply(Helper in) {
        HelperReadListTO out = new HelperReadListTO();

        out.setId(in.getId());
        out.setLastname(in.getLastname());
        out.setFirstname(in.getFirstname());

        if (in.getLastname() != null)
            out.setNationality(in.getNationality());

        out.setDob(in.getDob());
        out.setPhone(in.getPhone());
        out.setEmail(in.getEmail());
        out.setAdresse(in.getAdresse());
        out.setGender(in.getGender());
        out.setHelperType(in.getHelperType());

        return out;
    }

    public static List<HelperReadListTO> apply(List<Helper> helpers) {
        return helpers.stream().map(Helper2HelperReadListTO::apply).collect(Collectors.toList());
    }
}
