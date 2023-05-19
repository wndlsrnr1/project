package com.yet.project.repository.mybatismapper.item;

import com.yet.project.domain.item.*;
import com.yet.project.domain.user.UserKakao;
import com.yet.project.repository.dao.item.ItemDao;
import com.yet.project.repository.mybatismapper.user.UserMapper;
import com.yet.project.service.AJ;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@MapperScan("com.yet.project.repository.mybatismapper")
@Transactional
class ItemMapperTest {

    @Autowired
    ItemMapper itemMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    ItemDao itemDao;

    @Test
    void itemMapperNotNull() {
        AJ.assertThat(itemMapper).isNotNull();
    }

    @Test
    void getUserMapper() {
        UserKakao userKakao = userMapper.selectKakaoUserByKakaoId(2684628534l);
        AJ.assertThat(userKakao).isEqualTo(userKakao);
    }

    @Test
    void insertCategoryTest() {
        //given
        Category category = new Category();
        category.setName("hihi");
        category.setNameKor("히히");

        //when
        Integer result = itemMapper.insertCategory(category);
        Category newCategory = itemMapper.selectCategoryByName(category.getName());
        category.setId(newCategory.getId());

        //then
        AJ.assertThat(result).isEqualTo(1);
        AJ.assertThat(newCategory).isEqualTo(category);
    }

    @Test
    void insertBrandTest() {
        Brand brand = new Brand();
        brand.setName("sony");
        brand.setNameKor("소니");

        Long result = itemMapper.insertBrand(brand);
        Brand newBrand = itemMapper.selectBrandByName(brand.getName());

        AJ.assertThat(brand).isEqualTo(newBrand);
    }

    @Test
    void insertSubCategoryTest() {
        Subcategory subcategory = new Subcategory();
        subcategory.setName("sub");
        subcategory.setNameKor("서브");

        Long result = itemMapper.insertSubcategory(subcategory);
        Subcategory newSubcategory = itemMapper.selectSubcategoryByName(subcategory.getName());
        subcategory.setId(newSubcategory.getId());

        AJ.assertThat(subcategory).isEqualTo(newSubcategory);
    }

    @Test
    void insertCategorySubCategoryTest() {
        Category category = new Category();
        category.setName("hihi");
        category.setNameKor("히히");

        //when
        Integer result = itemMapper.insertCategory(category);
        Category newCategory = itemMapper.selectCategoryByName(category.getName());

        Subcategory subcategory = new Subcategory();
        subcategory.setName("sub");
        subcategory.setNameKor("서브");

        Long result2 = itemMapper.insertSubcategory(subcategory);
        Subcategory newSubcategory = itemMapper.selectSubcategoryByName(subcategory.getName());

        SubcategoryCategory subcategoryCategory = new SubcategoryCategory();
        subcategoryCategory.setCategoryId(newCategory.getId());
        subcategoryCategory.setSubcategoryId(newSubcategory.getId());

        itemMapper.insertSubcategoryCategory(subcategoryCategory);
        SubcategoryCategory newSubcategoryCategory = itemMapper.selectSubCategoryCategoryBySubcategoryId(newSubcategory.getId());

        AJ.assertThat(subcategoryCategory).isEqualTo(newSubcategoryCategory);
    }

    @Test
    void insertItemTest() {
        Item item = new Item();
        item.setName("ps5");
        item.setNameKor("플레이스테이션5");
        item.setPrice(5000l);
        item.setQuantity(100l);

        Long rows = itemMapper.insertItem(item);
        List<Item> insertedItem = itemMapper.selectItemsByName(item.getName());
        System.out.println("insertedItem = " + insertedItem);
        System.out.println("item = " + item);
        AJ.assertThat(insertedItem).contains(item);
    }

    @Test
    void insertItemBrandTest() {
        Item item = new Item();
        item.setName("ps5");
        item.setNameKor("플레이스테이션5");
        item.setPrice(5000l);
        item.setQuantity(100l);

        Long rows = itemMapper.insertItem(item);
        List<Item> insertedItem = itemMapper.selectItemsByName(item.getName());

        Long itemId = item.getId();
        System.out.println("itemId = " + itemId);
        Brand brand = new Brand();
        brand.setName("sony");
        brand.setNameKor("소니");

        itemMapper.insertBrand(brand);

        Long brandId = brand.getId();
        ItemBrand itemBrand = new ItemBrand();
        itemBrand.setItemId(itemId);
        itemBrand.setBrandId(brandId);

        itemMapper.insertItemBrand(itemBrand);

        ItemBrand itemBrand1 = itemMapper.selectItemBrandByItemId(itemBrand.getItemId());
        AJ.assertThat(itemBrand1.getItemId()).isEqualTo(itemId);

    }

    @Test
    void insertItemBrandSubCategoryTest() {
        Item item = new Item();
        item.setName("ps5");
        item.setNameKor("플레이스테이션5");
        item.setPrice(5000l);
        item.setQuantity(100l);

        Long rows = itemMapper.insertItem(item);

        Long itemId = item.getId();

        Subcategory subcategory = new Subcategory();
        subcategory.setName("sub");
        subcategory.setNameKor("서브");

        itemMapper.insertSubcategory(subcategory);

        ItemSubcategory itemSubcategory = new ItemSubcategory();
        itemSubcategory.setItemId(itemId);
        itemSubcategory.setSubcategoryId(subcategory.getId());

        itemMapper.insertItemSubcategory(itemSubcategory);

        ItemSubcategory itemSubcategory1 = itemMapper.selectItemSubcategoryByItemId(itemSubcategory.getSubcategoryId());
        
        AJ.assertThat(itemSubcategory1.getItemId()).isEqualTo(itemSubcategory.getItemId());
    }

    @Test
    void saveCategoryTest() {
        Category category = new Category();
        category.setName("haha");
        category.setNameKor("하하");
        itemDao.saveCategory(category);
        Category categoryById = itemDao.findCategoryById(category.getId());
        AJ.assertThat(categoryById.getId()).isEqualTo(category.getId());
    }

    @Test
    void insertTotalSubcategoryTest() {
        Category category = new Category();
        Subcategory subcategory = new Subcategory();

        //ItemDao.saveSubcategory(category, subcategory);
    }

    @Test
    void deleteCategory() {
        Category category = new Category();
        category.setName("haha");
        category.setNameKor("하하");
        itemDao.saveCategory(category);
        Category categoryById = itemDao.findCategoryById(category.getId());
        AJ.assertThat(categoryById.getId()).isEqualTo(category.getId());
        itemDao.removeCategoryById(categoryById.getId());
        Category categoryById1 = itemDao.findCategoryById(category.getId());
        AJ.assertThat(categoryById1).isNull();

    }
}