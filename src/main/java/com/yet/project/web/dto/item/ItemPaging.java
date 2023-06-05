package com.yet.project.web.dto.item;

import lombok.Data;

import java.util.List;

@Data
public class ItemPaging {
    private List<ItemJoined> itemList;
    private Long total;
}
