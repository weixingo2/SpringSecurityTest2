package com.springSecurity.mapper.product.ShopingSliders;

import com.springSecurity.entity.ShoopingCategory;
import com.springSecurity.entity.ShopingSliders;
import com.springSecurity.entity.ShoppingIcon;
import com.springSecurity.entity.ShoppingRecruitmentName;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ShopingSlidersMapper {


    @Insert("insert into shopping_sliders(url)values(#{url})")
    void insert(String url);

    @Select("select * from shopping_sliders")
    List<ShopingSliders> getList();

    @Insert("insert into shopping_icon(iconPath)values(#{iconPath})")
    void insertIcon(String iconPath);

    @Select("select * from shopping_icon")
    List<ShoppingIcon> getListIcon();

    @Insert("insert into shopping_recruitment(url)values(#{url})")
    void insertShoppingRecruit(String url);

    @Select("select * from shopping_recruitment")
    List<ShoppingRecruitmentName> getAll();

    @Insert("insert into shopping_category(url)values(#{url})")
    void insertShoppingCategory(String url);


    List<ShoopingCategory> getCategoryList(String recruitmentName);

    @Select("select sc.* from shopping_recruitment sc where sc.id=#{id}")
    ShoppingRecruitmentName getId(Integer id);
}
