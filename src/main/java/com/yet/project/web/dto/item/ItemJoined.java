package com.yet.project.web.dto.item;

import lombok.Data;

@Data
public class ItemJoined {
    private Long id;
    private String name;
    private String nameKor;
    private Long quantity;
    private Long price;
    private String brandNameKor;
    private String categoryNameKor;
    private String subcategoryNameKor;
    private Long brandId;
    private Long categoryId;
    private Long subcategoryId;
}
