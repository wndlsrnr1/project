package com.yet.project.repository.dao.item;

import com.yet.project.domain.item.*;
import com.yet.project.repository.mybatismapper.item.ItemMapper;
import com.yet.project.web.dto.item.ItemJoined;
import com.yet.project.web.dto.item.SubCategoryJoined;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


import java.util.List;
@Slf4j
@Component
@RequiredArgsConstructor
public class ItemDaoImpl implements ItemDao {
    private final ItemMapper itemMapper;


    @Override
    public void saveCategory(Category category) {
        itemMapper.insertCategory(category);
    }

    @Override
    public Category findCategoryById(Long id) {
        return itemMapper.selectCategoryById(id);
    }

    @Override
    public List<Category> findCategoriesAll() {
        return itemMapper.selectCategoryAll();
    }

    @Override
    public void removeCategoryById(Long id) {
        itemMapper.deleteCategoryById(id);
    }

    @Override
    public List<Brand> findBrandsAll() {
        return itemMapper.selectBrandsAll();
    }

    @Override
    public void saveBrand(Brand brand) {
        itemMapper.insertBrand(brand);
    }

    @Override
    public void removeBrandById(Long id) {
        itemMapper.deleteBrandById(id);
    }

    @Override
    public void updateBrand(Brand brand) {
        itemMapper.updateBrand(brand);
    }

    @Override
    public void updateCategory(Category category) {
        itemMapper.updateCategory(category);
    }

    @Override
    public Long saveSubcategory(Subcategory subcategory) {
        itemMapper.insertSubcategory(subcategory);
        return subcategory.getId();
    }

    @Override
    public Long saveSubcategoryCategory(SubcategoryCategory subcategoryCategory) {
        itemMapper.insertSubcategoryCategory(subcategoryCategory);
        return subcategoryCategory.getSubcategoryId();
    }

    @Override
    public List<SubCategoryJoined> selectSubCategoryAll() {
        return itemMapper.selectSubCategoryAll();
    }

    @Override
    public void removeSubcategoryById(Long id) {
        itemMapper.deleteSubcategoryById(id);
    }

    @Override
    public void updateSubcategory(Long categoryId, Subcategory subcategory) {
        itemMapper.updateSubcategory(categoryId, subcategory);
    }

    @Override
    public List<ItemJoined> findItemJoinedList() {
        return itemMapper.selectItemBrandCategorySubcategoryJoined();
    }

    @Override
    public void addItem(Item item) {
        itemMapper.insertItem(item);
    }

    @Override
    public void addItemBrand(ItemBrand itemBrand) {
        itemMapper.insertItemBrand(itemBrand);
    }

    @Override
    public void addItemSubcategory(ItemSubcategory itemSubcategory) {
        itemMapper.insertItemSubcategory(itemSubcategory);
    }


}
