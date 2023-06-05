package com.yet.project.web.dto.item;

import lombok.Data;

@Data
public class EditItemForm {
    private Long id;
    private String name;
    private String nameKor;
    private Long price;
    private Long quantity;
    private Long brandId;
    private Long categoryId;
    private Long subcategoryId;
}
