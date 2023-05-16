package com.yet.project.domain.service.item;

import com.yet.project.domain.item.*;
import com.yet.project.repository.dao.item.ItemDao;
import com.yet.project.repository.mybatismapper.item.ItemMapper;
import com.yet.project.web.dto.item.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ItemService {
    private final ItemDao itemDao;
    private final ItemMapper itemMapper;

    public void addCategory(Category category) {
        itemDao.saveCategory(category);
    }


    public List<Category> getCategoryListAll() {
        return itemDao.findCategoriesAll();
    }


    public boolean removeCategoryById(Long id) {
        try {
            itemDao.removeCategoryById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    public List<Brand> getBrandListAll() {
        return itemDao.findBrandsAll();
    }

    public void addBrand(Brand brand) {
        itemDao.saveBrand(brand);
    }

    public boolean removeBrandById(Long id) {
        try {
            itemDao.removeBrandById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void updateBrand(Brand brand) {
        itemDao.updateBrand(brand);
    }

    public void updateCategory(Category category) {
        itemDao.updateCategory(category);
    }


    public void addSubcategory(AddSubcategoryForm addSubcategoryForm) {
        Long categoryId = addSubcategoryForm.getCategoryId();
        if (categoryId == null) {
            return;
        }
        Subcategory subcategory = new Subcategory();
        subcategory.setName(addSubcategoryForm.getName());
        subcategory.setNameKor(addSubcategoryForm.getNameKor());
        itemDao.saveSubcategory(subcategory);

        Long subcategoryId = subcategory.getId();
        SubcategoryCategory subcategoryCategory = new SubcategoryCategory();
        subcategoryCategory.setCategoryId(categoryId);
        subcategoryCategory.setSubcategoryId(subcategoryId);
        itemDao.saveSubcategoryCategory(subcategoryCategory);
    }

    public Map<Long, List<Subcategory>> getSubcategoryAllByCategoryId() {
        HashMap<Long, List<Subcategory>> result = new HashMap<>();
        List<SubCategoryJoined> subCategoryJoinedList = itemDao.selectSubCategoryAll();
        subCategoryJoinedList.forEach(elem -> {
            Long key = elem.getCategoryId();
            Subcategory subcategory = new Subcategory();
            subcategory.setId(elem.getSubcategoryId());
            subcategory.setName(elem.getName());
            subcategory.setNameKor(elem.getNameKor());

            List<Subcategory> subcategoryList = result.computeIfAbsent(key, k -> new ArrayList<>());
            subcategoryList.add(subcategory);
        });
        return result;
    }

    public boolean removeSubcategoryById(Long id) {
        try {
            itemDao.removeSubcategoryById(id);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public void renameSubcategory(UpdateSubcategory updateSubcategoryForm) {
        Subcategory subcategory = new Subcategory();
        subcategory.setNameKor(updateSubcategoryForm.getNameKor());
        subcategory.setName(updateSubcategoryForm.getName());
        subcategory.setId(updateSubcategoryForm.getId());

        Long categoryId = updateSubcategoryForm.getCategoryId();
        itemDao.updateSubcategory(categoryId, subcategory);
    }

    public List<ItemJoined> findItemJoinedList() {
        return itemDao.findItemJoinedList();
    }

    public void addItem(ItemInsertForm itemInsertForm) {
        //Item Insert
        Item item = new Item();
        item.setNameKor(itemInsertForm.getNameKor());
        item.setName(itemInsertForm.getName());
        item.setQuantity(itemInsertForm.getQuantity());
        item.setPrice(itemInsertForm.getPrice());
        itemDao.addItem(item);


        //itembrand insert
        ItemBrand itemBrand = new ItemBrand();
        itemBrand.setItemId(item.getId());
        itemBrand.setBrandId(itemInsertForm.getBrandId());
        itemDao.addItemBrand(itemBrand);

        //itemSubcategory Insert
        ItemSubcategory itemSubcategory = new ItemSubcategory();
        itemSubcategory.setSubcategoryId(itemInsertForm.getSubcategoryId());
        itemSubcategory.setItemId(item.getId());
        itemDao.addItemSubcategory(itemSubcategory);
    }

    public void loadAll(Model model) {
        List<Category> categoryListAll = getCategoryListAll();
        model.addAttribute("categoryList", categoryListAll);

        //BrandList from DB
        List<Brand> brandList = getBrandListAll();
        model.addAttribute("brandList", brandList);

        Map<Long, List<Subcategory>> subcategoryByCategory = getSubcategoryAllByCategoryId();
        model.addAttribute("subcategoryByCategory", subcategoryByCategory);
    }

    public List<ItemJoined> getItemsByCondition(
            Long id, Long quantity1, Long quantity2,
            Long price1, Long price2, Long brandId,
            Long categoryId, Long subcategoryId
    ) {
        if ( id == null &&  quantity1 == null &&  quantity2 == null &&  price1 == null &&  price2 == null &&  brandId == null &&  categoryId == null &&  subcategoryId == null) {
            List<Item> itemList = itemMapper.selectItemsLimit15();

            Map<Long, Brand> itemBrandMap = itemDao.selectBrandByItemIds(itemList);

            Map<Long, Subcategory> itemSubCategoryMap = itemDao.selectSubcategoryByItemIds(itemList);

            Map<Long, Category> subcategoryCategoryMap = itemDao.selectCategoriesBySubcategoryIds(itemSubCategoryMap.values().stream().collect(Collectors.toList()));

            Map<Long, ItemJoined> itemJoinedMap = new HashMap<>();

            for (Item item : itemList) {
                Long itemId = item.getId();
                ItemJoined itemJoined = new ItemJoined();
                itemJoined.setId(itemId);
                itemJoined.setName(item.getName());
                itemJoined.setNameKor(item.getNameKor());
                itemJoined.setPrice(item.getPrice());
                itemJoined.setQuantity(item.getQuantity());
                if (itemBrandMap.containsKey(itemId) && itemBrandMap.get(itemId) != null) {
                    itemJoined.setBrandNameKor(itemBrandMap.get(itemId).getNameKor());
                }
                if (itemSubCategoryMap.containsKey(itemId) && itemSubCategoryMap.get(itemId) != null) {
                    Subcategory subcategory = itemSubCategoryMap.get(itemId);
                    itemJoined.setSubcategoryNameKor(subcategory.getNameKor());
                    if (subcategoryCategoryMap.containsKey(subcategory.getId()) && subcategoryCategoryMap.get(subcategory.getId()) != null) {
                        itemJoined.setCategoryNameKor(subcategoryCategoryMap.get(itemSubCategoryMap.get(itemId).getId()).getNameKor());
                    }
                }

                itemJoinedMap.put(itemId, itemJoined);
            }

            return new ArrayList<>(itemJoinedMap.values());
        }

        //조건 검색
        return null;
    }

    public boolean removeItemByItemId(Long itemId) {
        return itemDao.deleteItemByItemId(itemId);
    }
}
