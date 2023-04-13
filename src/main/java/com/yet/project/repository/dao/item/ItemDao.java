package com.yet.project.repository.dao.item;

import com.yet.project.domain.item.*;
import com.yet.project.web.dto.item.ItemJoined;
import com.yet.project.web.dto.item.SubCategoryJoined;

import java.util.List;

public interface ItemDao {

    void saveCategory(Category category);

    Category findCategoryById(Long id);

    List<Category> findCategoriesAll();

    void removeCategoryById(Long id);

    List<Brand> findBrandsAll();

    void saveBrand(Brand brand);

    void removeBrandById(Long id);

    void updateBrand(Brand brand);

    void updateCategory(Category category);

    Long saveSubcategory(Subcategory subcategory);

    Long saveSubcategoryCategory(SubcategoryCategory subcategoryCategory);

    List<SubCategoryJoined> selectSubCategoryAll();

    void removeSubcategoryById(Long id);

    void updateSubcategory(Long categoryId, Subcategory subcategory);

    List<ItemJoined> findItemJoinedList();

    void addItem(Item item);

    void addItemBrand(ItemBrand itemBrand);

    void addItemSubcategory(ItemSubcategory itemSubcategory);
}
