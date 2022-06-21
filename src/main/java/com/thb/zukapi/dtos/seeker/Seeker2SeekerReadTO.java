package com.thb.zukapi.dtos.seeker;

import com.thb.zukapi.models.Seeker;

import java.util.List;
import java.util.stream.Collectors;

public class Seeker2SeekerReadTO {

    public static SeekerReadTO apply(Seeker in) {
        SeekerReadTO out = new SeekerReadTO();

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

        if (in.getPassword() != null)
            out.setPassword(in.getPassword());

        out.setRole(in.getRole());

        if (in.getAnnouncements() != null)
            out.setAnnouncements(in.getAnnouncements());

        return out;
    }

    public static List<SeekerReadTO> apply(List<Seeker> seekers) {
        return seekers.stream().map(Seeker2SeekerReadTO::apply).collect(Collectors.toList());
    }

}
