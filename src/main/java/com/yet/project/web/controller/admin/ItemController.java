package com.yet.project.web.controller.admin;

import com.yet.project.domain.item.Brand;
import com.yet.project.domain.item.Category;
import com.yet.project.domain.item.Item;
import com.yet.project.domain.item.Subcategory;
import com.yet.project.domain.service.item.ItemService;
import com.yet.project.web.dto.item.AddSubcategoryForm;
import com.yet.project.web.dto.item.ItemJoined;
import com.yet.project.web.dto.item.UpdateSubcategory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;

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
    public String itemsViewRequest(
        @RequestParam(value = "search", defaultValue = "false") Boolean search, Model model
    ) {
        if (!search) {
            List<ItemJoined> itemList = itemService.findItemJoinedList();
            //model.addAttribute("itemList", itemList);
            return "/admin/items/items";
        }
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
    public String categoriesManageViewRequest(@ModelAttribute("category") Category category, Model model, @ModelAttribute("brand") Brand brand, @ModelAttribute("addSubcategoryForm") AddSubcategoryForm addSubcategoryForm, @ModelAttribute("subcategory") Subcategory subcategory){
        //category List from DB
        List<Category> categoryListAll = itemService.getCategoryListAll();
        model.addAttribute("categoryList", categoryListAll);

        //BrandList from DB
        List<Brand> brandList = itemService.getBrandListAll();
        model.addAttribute("brandList", brandList);

        Map<Long, List<Subcategory>> subcategoryByCategory = itemService.getSubcategoryAllByCategoryId();
        model.addAttribute("subcategoryByCategory", subcategoryByCategory);

        return "/admin/items/categoriesManage";
    }

    @PostMapping("/item/categories/manage/category/add")
    public String addCategory(@ModelAttribute("category") Category category) {
        //검증

        itemService.addCategory(category);

        return "redirect:/admin/item/categories/manage";
    }

    @PostMapping("/item/categories/manage/category/remove")
    public String removeCategory(Long id) {
        //검증
        boolean b = itemService.removeCategoryById(id);
        return "redirect:/admin/item/categories/manage";
    }

    @PostMapping("/item/categories/manage/category/update")
    public String updateCategory(Category category) {

        itemService.updateCategory(category);
        return "redirect:/admin/item/categories/manage";
    }

    @PostMapping("/item/categories/manage/brand/add")
    public String addBrand(@ModelAttribute("brand") Brand brand) {
        //검증

        itemService.addBrand(brand);

        return "redirect:/admin/item/categories/manage";
    }

    @PostMapping("/item/categories/manage/brand/remove")
    public String removeBrand(Long id) {
        //검증
        boolean b = itemService.removeBrandById(id);
        return "redirect:/admin/item/categories/manage";
    }

    @PostMapping("/item/categories/manage/brand/update")
    public String updateBrand(Brand brand) {
        itemService.updateBrand(brand);
        return "redirect:/admin/item/categories/manage";
    }

    @PostMapping("/item/categories/manage/subcategory/add")
    public String addSubcategory(@ModelAttribute("subcategory") AddSubcategoryForm addSubcategoryForm) {
        //검증
        log.info("addSubcategoryForm {}", addSubcategoryForm);
        itemService.addSubcategory(addSubcategoryForm);
        return "redirect:/admin/item/categories/manage";
    }

    @PostMapping("/item/categories/manage/subcategory/remove")
    public String removeSubcategory(Long id) {
        //검증
        boolean b = itemService.removeSubcategoryById(id);
        return "redirect:/admin/item/categories/manage";
    }

    @PostMapping("/item/categories/manage/subcategory/update")
    public String updateSubcategory(UpdateSubcategory updateSubcategoryForm) {
        itemService.renameSubcategory(updateSubcategoryForm);
        return "redirect:/admin/item/categories/manage";
    }


}
