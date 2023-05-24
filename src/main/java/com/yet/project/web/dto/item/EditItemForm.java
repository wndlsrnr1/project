package com.yet.project.web.dto.item;

import lombok.Data;

@Data
public class EditItemForm {
    Long id;
    String name;
    String nameKor;
    Long price;
    Long quantity;
    Long brandId;
    Long categoryId;
    Long subcategoryId;
}
