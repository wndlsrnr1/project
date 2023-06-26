package com.yet.project.web.controller.admin.item;

import com.yet.project.domain.item.Brand;
import com.yet.project.domain.item.Category;
import com.yet.project.domain.item.Item;
import com.yet.project.domain.item.Subcategory;
import com.yet.project.domain.item.validator.BrandValidator;
import com.yet.project.domain.item.validator.CategoryValidator;
import com.yet.project.domain.service.common.Util;
import com.yet.project.domain.service.item.ItemService;
import com.yet.project.web.dto.item.AddSubcategoryForm;
import com.yet.project.web.dto.item.ItemJoined;
import com.yet.project.web.dto.item.UpdateSubcategory;
import com.yet.project.web.dto.request.item.AddEventForm;
import com.yet.project.web.dto.response.common.APIResponseEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class ItemController {

    private final ItemService itemService;
    private final Util util;
    private final CategoryValidator categoryValidator;
    private final BrandValidator brandValidator;

    //여러 모델 객체에 대해서 assign이 돌아가서 문제 생길 수 있음.
//    @InitBinder("category")
//    public void initBinder(WebDataBinder webDataBinder) {
//        log.info("webDataBinder {}", webDataBinder);
//        webDataBinder.addValidators(categoryValidator);
//    }

    @InitBinder("brand")
    public void initBinderBrand(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(brandValidator);
    }



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
        itemService.loadAll(model);
//        //category List from DB
//        List<Category> categoryListAll = itemService.getCategoryListAll();
//        model.addAttribute("categoryList", categoryListAll);
//
//        //BrandList from DB
//        List<Brand> brandList = itemService.getBrandListAll();
//        model.addAttribute("brandList", brandList);
//
//        Map<Long, List<Subcategory>> subcategoryByCategory = itemService.getSubcategoryAllByCategoryId();
//        model.addAttribute("subcategoryByCategory", subcategoryByCategory);

        return "/admin/items/categoriesManage";
    }

    @PostMapping("/item/categories/manage/category/add")
    public String addCategory(@ModelAttribute("category") Category category, BindingResult bindingResult, @ModelAttribute("brand") Brand brand) {
        //검증
        if (bindingResult.hasErrors()) {
            return "/admin/items/categoriesManage";
        }

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
    public String addBrand(@Validated @ModelAttribute("brand") Brand brand, BindingResult bindingResult, @ModelAttribute("category") Category category, Model model, @ModelAttribute("addSubcategoryForm") AddSubcategoryForm addSubcategoryForm, @ModelAttribute("subcategory") Subcategory subcategory) {
        //검증
        if (bindingResult.hasErrors()) {
            log.info("asdfasdfadsf {}", bindingResult);
            itemService.loadAll(model);
            return "/admin/items/categoriesManage";
        }
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

    @GetMapping("/items/search")
    public ResponseEntity requestSubmitSearch(@RequestParam(name = "item_name", required = false) String itemName) {
        List<Item> itemList = itemService.getItemsUpTo15(itemName);
        return APIResponseEntity.success(itemList);
    }




}
