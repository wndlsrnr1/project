package com.yet.project.web.dto.item;

import lombok.Data;

@Data
public class ItemInsertForm {
    Long id;
    String name;
    String nameKor;
    Long brandId;
    Long subcategoryId;
    Long price;
    Long quantity;
}
