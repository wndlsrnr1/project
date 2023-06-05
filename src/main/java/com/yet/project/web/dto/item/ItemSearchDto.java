package com.yet.project.web.dto.item;
import lombok.Data;

@Data
public class ItemSearchDto {
    private Long id;
    private Long brandId1;
    private Long brandId2;
    private Long categoryId1;
    private Long categoryId2;
    private Long subcategoryId1;
    private Long subcategoryId2;
    private Long quantity1;
    private Long quantity2;
    private Long price1;
    private Long price2;
    private String itemName;
    private Long page;
    private Long perPage;
}
