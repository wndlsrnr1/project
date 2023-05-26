package com.yet.project.web.dto.item;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class AddItemForm {
    String name;
    String nameKor;
    Long price;
    Long quantity;
    Long brandId;
    Long categoryId;
    Long subcategoryId;
    List<MultipartFile> images;
}
