package com.yet.project.repository.mybatismapper.item;

import com.yet.project.domain.item.*;
import com.yet.project.web.dto.item.ItemJoined;
import com.yet.project.web.dto.item.SubCategoryJoined;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ItemMapper {
    //insert

    //category
    @Insert("INSERT INTO category (name, name_kor) VALUES (#{name}, #{nameKor})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Integer insertCategory(Category category);

    //brand
    @Insert("INSERT INTO BRAND (name, name_kor) VALUES (#{name}, #{nameKor})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Long insertBrand(Brand brand);

    //subcategory
    @Insert("insert into subcategory (name, name_kor) values (#{name}, #{nameKor})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Long insertSubcategory(Subcategory subcategory);

    @Insert("insert into subcategory_category (category_id, subcategory_id) values (#{categoryId}, #{subcategoryId})")
    void insertSubcategoryCategory(SubcategoryCategory subcategoryCategory);

    @Insert("insert into item (name, name_kor, price, quantity) values (#{name}, #{nameKor}, #{price}, #{quantity})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Long insertItem(Item item);

    @Insert("insert into item_brand (item_id, brand_id) values(#{itemId}, #{brandId})")
    void insertItemBrand(ItemBrand itemBrand);

    @Insert("insert into item_subcategory (item_id, subcategory_id) values(#{itemId}, #{subcategoryId})")
    void insertItemSubcategory(ItemSubcategory itemSubcategory);

    //select
    @Select("select id, name, name_kor from category where name=#{name}")
    Category selectCategoryByName(String name);

    @Select("select id, name, name_kor from brand where name=#{name}")
    Brand selectBrandByName(String name);

    @Select("select id, name, name_kor from subcategory where name=#{name}")
    Subcategory selectSubcategoryByName(String name);

    @Select("select category_id, subcategory_id from subcategory_category where subcategory_id = #{subcategoryId}")
    SubcategoryCategory selectSubCategoryCategoryBySubcategoryId(long subcategoryId);

    @Select("select id, name, name_kor, price, quantity from item where name = #{name}")
    List<Item> selectItemsByName(String name);

    @Select("select item_id, brand_id from item_brand where item_id = #{itemId}")
    ItemBrand selectItemBrandByItemId(Long itemId);

    @Select("select item_id, subcategory_id from item_subcategory where subcategory_id = #{subcategoryId}")
    ItemSubcategory selectItemSubcategoryBySubcategoryId(Long subcategoryId);


    @Select("select item_id, subcategory_id from item_subcategory where item_id = #{itemId}")
    ItemSubcategory selectItemSubcategoryByItemId(Long subcategoryId);

    @Select("select id, name, name_kor from category where id = #{id}")
    Category selectCategoryById(Long id);

    @Select("select id, name, name_kor from category limit 100")
    List<Category> selectCategoryAll();

    //delete from users where uid = #{uid}
    @Delete("delete from category where id = #{id}")
    void deleteCategoryById(Long id);

    @Select("select id, name, name_kor from brand limit 100")
    List<Brand> selectBrandsAll();

    @Delete("delete from brand where id = #{id}")
    void deleteBrandById(Long id);

    @Update("UPDATE brand SET name = #{name}, name_kor = #{nameKor} WHERE id = #{id}")
    void updateBrand(Brand brand);

    @Update("update category set name = #{name}, name_kor = #{nameKor} where id = #{id}")
    void updateCategory(Category category);


    @Select("SELECT category_id, subcategory_id FROM subcategory_category")
    List<SubcategoryCategory> selectSubCategoryCategoryAll();

    @Select("SELECT c.subcategory_id, s.name, s.name_kor c.category_id FROM subcategory_category c JOIN subcategory s ON c.subcategory_id = s.id WHERE c.categoryId = #{categoryId}")
    List<Subcategory> selectSubCategoryAllById(Long categoryId);

    @Select("SELECT c.subcategory_id, s.name, s.name_kor, c.category_id FROM subcategory s left JOIN subcategory_category c ON c.subcategory_id = s.id")
    List<SubCategoryJoined> selectSubCategoryAll();

    @Delete("delete from subcategory where id = #{id}")
    void deleteSubcategoryById(Long id);

    @Update("UPDATE subcategory SET name=#{subcategory.name}, name_kor=#{subcategory.nameKor} WHERE id=#{subcategory.id}")
    void updateSubcategory(@Param("categoryId") Long categoryId, @Param("subcategory") Subcategory subcategory);

    @Select("SELECT i.id, i.name, i.name_kor, i.quantity, i.price, b.name as brand_name, b.name_kor as brand_name_kor, s.name_kor as subcategory_name_kor, c.name_kor as category_name_kor FROM item i " +
        "JOIN item_brand ib ON i.id = ib.item_id " +
        "JOIN brand b ON ib.brand_id = b.id " +
        "JOIN item_subcategory isub ON i.id = isub.item_id " +
        "JOIN subcategory s ON isub.subcategory_id = s.id " +
        "JOIN subcategory_category scc ON s.id = scc.subcategory_id " +
        "JOIN category c ON scc.category_id = c.id order by id asc limit 100")
    List<ItemJoined> selectItemBrandCategorySubcategoryJoined();




    /*
    @Select("select uid, email, password from users where email = #{email}")
    User selectUserByEmail(String email);

    @Insert("INSERT INTO users (name, email, phone, password, auth) VALUES (#{name}, #{email}, #{phone}, #{password}, #{auth})")
    @Options(useGeneratedKeys = true, keyProperty = "uid")
    Long insertUser(User user);
     */


}
