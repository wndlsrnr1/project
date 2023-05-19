package com.yet.project.web.dto.item;

import lombok.Data;

@Data
public class SubCategoryJoined {
    Long subcategoryId;
    Long categoryId;
    String name;
    String nameKor;
}
