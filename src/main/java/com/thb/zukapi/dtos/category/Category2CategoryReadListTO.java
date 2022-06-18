package com.thb.zukapi.dtos.category;

import com.thb.zukapi.models.Category;

import java.util.List;
import java.util.stream.Collectors;

public class Category2CategoryReadListTO {
    public static CategoryReadListTO apply(Category in) {
        CategoryReadListTO out = new CategoryReadListTO();

        out.setId(in.getId());
        out.setCover(in.getCover());
        out.setName(in.getName());

        return out;
    }

    public static List<CategoryReadListTO> apply(List<Category> categories) {
        return categories.stream().map(Category2CategoryReadListTO::apply).collect(Collectors.toList());
        // categories.stream().map(category -> apply(category)).collect(Collectors.toList());
    }
}
