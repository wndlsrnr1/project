package com.yet.project.web.dto.item;

import lombok.Data;

@Data
public class ItemInsertForm {
    private Long id;
    private String name;
    private String nameKor;
    private Long brandId;
    private Long subcategoryId;
    private Long price;
    private Long quantity;
}
