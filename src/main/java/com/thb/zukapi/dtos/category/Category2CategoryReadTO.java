package com.thb.zukapi.dtos.category;

import com.thb.zukapi.models.Category;

import java.util.List;
import java.util.stream.Collectors;

public class Category2CategoryReadTO {
    public static CategoryReadTO apply(Category in) {
        CategoryReadTO out = new CategoryReadTO();

        out.setId(in.getId());
        out.setCover(in.getCover());
        out.setName(in.getName());
        out.setAnnouncements(in.getAnnouncements());

        return out;
    }

    public static List<CategoryReadTO> apply(List<Category> categories) {
        return categories.stream().map(Category2CategoryReadTO::apply).collect(Collectors.toList());
        // categories.stream().map(category -> apply(category)).collect(Collectors.toList());
    }
}
