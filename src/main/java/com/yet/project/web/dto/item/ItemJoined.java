package com.yet.project.web.dto.item;

import lombok.Data;

@Data
public class ItemJoined {
    Long id;
    String name;
    String nameKor;
    Long quantity;
    Long price;
    String brandNameKor;
    String categoryNameKor;
    String subcategoryNameKor;
    Long brandId;
    Long categoryId;
    Long subcategoryId;
}
