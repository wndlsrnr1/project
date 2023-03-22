package com.yet.project.web.controller.admin;

import com.yet.project.domain.item.Item;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class ItemController {

    @ModelAttribute("item")
    public Item addItemTemporary() {
        Item item = new Item();
        item.setId(1L);
        item.setName("PS5");
        item.setNameKor("플레이스테이션5");
        item.setPrice(1000l);
        item.setQuantity(100l);
        return item;
    }

    @GetMapping("/items")
    public String itemsViewRequest() {
        return "/admin/items/items";
    }

    @GetMapping("/item/{itemId}")
    public String itemDetailViewRequest(@PathVariable Long itemId) {
        return "/admin/items/item";
    }

    @GetMapping("/item/{itemId}/edit")
    public String itemEditViewRequest(@PathVariable Long itemId) {
        return "/admin/items/itemEdit";
    }

    @GetMapping("/item/add")
    public String itemAddViewRequest() {
        return "/admin/items/itemAdd";
    }

    @GetMapping("/item/categories/manage")
    public String CategoriesManageViewRequest() {
        return "/admin/items/categoriesManage";
    }

}
