package com.yet.project.domain.item;

import lombok.Data;

@Data
public class Item {
    Long id;
    String name;
    String nameKor;
    Long quantity;
    Long price;
}
