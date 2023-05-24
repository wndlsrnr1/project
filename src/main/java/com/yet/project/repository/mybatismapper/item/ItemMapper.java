package com.yet.project.repository.mybatismapper.item;

import com.yet.project.domain.item.*;
import com.yet.project.web.dto.item.EditItemForm;
import com.yet.project.web.dto.item.ItemJoined;
import com.yet.project.web.dto.item.ItemSearchDto;
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

    @Update("update subcategory set name=#{name}, name_kor = #{nameKor} where id = #{id}")
    Boolean updateSubcategoryByObject(Subcategory subcategory);

    @Select("SELECT i.id, i.name, i.name_kor, i.quantity, i.price, b.name as brand_name, b.name_kor as brand_name_kor, s.name_kor as subcategory_name_kor, c.name_kor as category_name_kor " +
            "FROM item i " +
            "JOIN item_brand ib ON i.id = ib.item_id " +
            "JOIN brand b ON ib.brand_id = b.id " +
            "JOIN item_subcategory isub ON i.id = isub.item_id " +
            "JOIN subcategory s ON isub.subcategory_id = s.id " +
            "JOIN subcategory_category scc ON s.id = scc.subcategory_id " +
            "JOIN category c ON scc.category_id = c.id order by id asc limit 100")
    List<ItemJoined> selectItemBrandCategorySubcategoryJoined();

    @Select("select id, name_kor, quantity, price from item order by id asc limit 15")
    List<Item> selectItemsLimit15();

    @Select("select b.id, b.name, b.name_kor from item_brand as ib join brand as b on ib.brand_id = b.id where ib.item_id = #{id}")
    Brand selectBrandByItemId(Long id);

    @Select("select s.id, s.name, s.name_kor from subcategory as s join item_subcategory as i on i.subcategory_id = s.id where i.item_id = #{id}")
    Subcategory selectSubcategoryByItemId(Long id);

    @Select("select c.id, c.name, c.name_kor from subcategory_category as sc left join category as c on sc.category_id = c.id where sc.subcategory_id = #{id}")
    Category selectCategoryBySubcategoryId(Long id);

    @Select("select id, name, name_kor from item where id=#{id}")
    Item selectItemById(Long id);

    @Delete("delete from Item where id = #{id}")
    boolean deleteItemById(Long id);

    @Select("SELECT S.ID, S.NAME, S.NAME_KOR FROM SUBCATEGORY_CATEGORY AS SC JOIN SUBCATEGORY AS S ON SC.SUBCATEGORY_ID = S.ID WHERE SC.CATEGORY_ID=#{categoryId}")
    List<Subcategory> selectSubcategoryByCategoryIdAll(Long categoryId);

    @Select("SELECT ID, NAME, NAME_KOR FROM SUBCATEGORY SUBCATEGORY LEFT JOIN SUBCATEGORY_CATEGORY MAPPING ON SUBCATEGORY.ID = MAPPING.SUBCATEGORY_ID WHERE MAPPING.CATEGORY_ID IS NULL")
    List<Subcategory> selectSubcategoryWhereCategoryIdNull();

    @Select("select sc.category_id, s.id, s.name, s.name_kor from subcategory as s " +
            "join subcategory_category as sc on sc.subcategory_id = s.id " +
            "where category_id = #{categoryId}")
    List<Subcategory> selectSubCategoryByCategoryId(Long categoryId);

    @Select("select id, name, name_kor from brand where id = #{brandId}")
    Brand selectBrandByBrandId(Long brandId);

    @Select("select id, name, name_kor from subcategory where id = #{subcategoryId}")
    Subcategory selectSubcategoryById(Long subcategoryId);

    @Insert("insert into item_category (item_id, category_id) values (#{itemId}, #{categoryId})")
    void insertItemCategory(ItemCategory itemCategory);

    @Select({
            "SELECT i.id, i.name, i.name_kor, i.price, i.quantity, item_s.subcategory_id, ib.brand_id, ic.category_id",
            "FROM item AS i",
            "left JOIN item_subcategory AS item_s ON i.id = item_s.item_id",
            "left JOIN item_brand AS ib ON ib.item_id = i.id",
            "left Join item_category as ic on ic.item_id = i.id",
            "WHERE 1=1",
            "AND item_s.subcategory_id >= #{subcategoryId1}",
            "AND item_s.subcategory_id <= #{subcategoryId2}",
            "AND ib.brand_id >= #{brandId1}",
            "AND ib.brand_id <= #{brandId2}",
            "and ic.category_id >= #{categoryId1}",
            "and ic.category_id <= #{categoryId2}",
            "AND i.quantity >= #{quantity1}",
            "AND i.quantity <= #{quantity2}",
            "AND i.price >= #{price1}",
            "AND i.price <= #{price2}",
            "AND (",
            "i.name LIKE CONCAT('%', #{itemName}, '%')",
            "OR i.name LIKE CONCAT(#{itemName}, '%')",
            "OR i.name LIKE CONCAT('%', #{itemName})",
            "OR i.name_kor LIKE CONCAT('%', #{itemName}, '%')",
            "OR i.name_kor LIKE CONCAT(#{itemName}, '%')",
            "OR i.name_kor LIKE CONCAT('%', #{itemName})",
            ")",
            "ORDER BY i.id limit #{page}, #{perPage}"
    })
    List<ItemJoined> selectItemJoinedByCondition(ItemSearchDto itemSearchDto);

    @Select({
            "SELECT i.id, i.name, i.name_kor, i.price, i.quantity, item_s.subcategory_id, ib.brand_id, ic.category_id",
            "FROM item AS i",
            "left JOIN item_subcategory AS item_s ON i.id = item_s.item_id",
            "left JOIN item_brand AS ib ON ib.item_id = i.id",
            "left Join item_category as ic on ic.item_id = i.id",
            "WHERE 1=1",
            "AND item_s.subcategory_id >= #{subcategoryId1}",
            "AND item_s.subcategory_id <= #{subcategoryId2}",
            "AND ib.brand_id >= #{brandId1}",
            "AND ib.brand_id <= #{brandId2}",
            "and ic.category_id >= #{categoryId1}",
            "and ic.category_id <= #{categoryId2}",
            "AND i.quantity >= #{quantity1}",
            "AND i.quantity <= #{quantity2}",
            "AND i.price >= #{price1}",
            "AND i.price <= #{price2}",
            "AND (",
            "i.name LIKE CONCAT('%', #{itemName}, '%')",
            "OR i.name LIKE CONCAT(#{itemName}, '%')",
            "OR i.name LIKE CONCAT('%', #{itemName})",
            "OR i.name_kor LIKE CONCAT('%', #{itemName}, '%')",
            "OR i.name_kor LIKE CONCAT(#{itemName}, '%')",
            "OR i.name_kor LIKE CONCAT('%', #{itemName})",
            ") limit 1000",
    })
    List<ItemSearchDto> countItemsByValue(ItemSearchDto filterValue);

    @Select({
            "select i.id, i.name, i.name_kor, i.price, i.quantity, ib.brand_id, ic.category_id, isc.subcategory_id ",
            "from item as i",
            "left join item_brand as ib on i.id = ib.item_id",
            "left join item_category as ic on i.id = ic.item_id",
            "left join item_subcategory as isc on i.id = isc.item_id",
            "where i.id = #{itemId}"
    })
    EditItemForm selectForJoinedEditFormByItemId(Long itemId);

    @Update("update item set name = #{name}, name_kor = #{nameKor}, price = #{price}, quantity = #{quantity} where id = #{id}")
    void updateItemByItem(Item item);

    @Update("update item_brand set brand_id = #{brandId} where item_id = #{itemId}")
    void updateItemBrandByBrandId(ItemBrand itemBrand);

    @Update("update item_category set category_id = #{categoryId} where item_id = #{itemId}")
    void updateItemCategory(ItemCategory itemCategory);

    @Update("update item_subcategory set subcategory_id = #{subcategoryId} where item_id = #{itemId}")
    void updateItemSubcategory(ItemSubcategory itemSubcategory);

    @Delete("delete from item_subcategory where itemId = #{itemId}")
    void deleteItemSubcategory(ItemSubcategory itemSubcategory);





    /*
    @Select("select uid, email, password from users where email = #{email}")
    User selectUserByEmail(String email);

    @Insert("INSERT INTO users (name, email, phone, password, auth) VALUES (#{name}, #{email}, #{phone}, #{password}, #{auth})")
    @Options(useGeneratedKeys = true, keyProperty = "uid")
    Long insertUser(User user);
     */


}
