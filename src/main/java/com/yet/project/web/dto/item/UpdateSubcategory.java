package com.yet.project.web.dto.item;

import lombok.Data;

@Data
public class UpdateSubcategory {
    private Long categoryId;
    private Long id;
    private String name;
    private String nameKor;
}
