package com.yet.project.web.dto.item;

import lombok.Data;

@Data
public class SubCategoryJoined {
    private Long subcategoryId;
    private Long categoryId;
    private String name;
    private String nameKor;
}
