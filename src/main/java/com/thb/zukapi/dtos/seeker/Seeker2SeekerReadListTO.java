package com.thb.zukapi.dtos.seeker;

import com.thb.zukapi.models.Seeker;

import java.util.List;
import java.util.stream.Collectors;

public class Seeker2SeekerReadListTO {

    public static SeekerReadListTO apply(Seeker in) {
        SeekerReadListTO out = new SeekerReadListTO();

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

        return out;
    }

    public static List<SeekerReadListTO> apply(List<Seeker> seekers) {
        return seekers.stream().map(Seeker2SeekerReadListTO::apply).collect(Collectors.toList());
    }
}
