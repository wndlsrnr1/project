package com.yet.project.web.dto.item;

import lombok.Data;

import java.util.List;

@Data
public class ItemPaging {
    List<ItemJoined> itemList;
    Long total;
}
