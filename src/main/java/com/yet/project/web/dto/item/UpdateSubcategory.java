package com.yet.project.web.dto.item;

import lombok.Data;

@Data
public class UpdateSubcategory {
    Long categoryId;
    Long id;
    String name;
    String nameKor;
}
