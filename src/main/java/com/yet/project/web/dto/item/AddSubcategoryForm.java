package com.yet.project.web.dto.item;

import lombok.Data;

@Data
public class AddSubcategoryForm {
    Long categoryId;
    Long id;
    String name;
    String nameKor;
}
