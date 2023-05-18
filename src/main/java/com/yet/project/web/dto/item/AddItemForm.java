package com.yet.project.web.dto.item;

import lombok.Data;

@Data
public class AddItemForm {
    String name;
    String nameKor;
    Long price;
    Long quantity;
    Long brandId;
    Long categoryId;
    Long subcategoryId;
}
