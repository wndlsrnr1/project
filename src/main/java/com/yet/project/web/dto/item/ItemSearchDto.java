package com.yet.project.web.dto.item;
import lombok.Data;

@Data
public class ItemSearchDto {
    Long id;
    Long brandId1;
    Long brandId2;
    Long categoryId1;
    Long categoryId2;
    Long subcategoryId1;
    Long subcategoryId2;
    Long quantity1;
    Long quantity2;
    Long price1;
    Long price2;
    String itemName;
    Long page;
    Long perPage;
}
