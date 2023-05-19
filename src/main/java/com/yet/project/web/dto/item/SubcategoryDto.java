package com.yet.project.web.dto.item;

import com.yet.project.domain.item.Subcategory;
import lombok.Data;

import java.util.List;

@Data
public class SubcategoryDto {
    Long categoryId;
    List<Subcategory> subcategoryList;
}
