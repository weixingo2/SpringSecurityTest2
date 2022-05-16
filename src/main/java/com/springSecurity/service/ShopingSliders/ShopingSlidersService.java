package com.springSecurity.service.ShopingSliders;

import com.github.pagehelper.PageInfo;
import com.springSecurity.entity.*;

import java.util.List;

public interface ShopingSlidersService {
    void insert(String s);

    List<ShopingSliders> getList();

    void insertIcon(String s);


    List<ShoppingIcon> getListIcon();

    void insertShoppingRecruit(String s);

    PageInfo<ShoppingRecruitmentName> geTAll(RecuritmentFrom from);

    void insertShoppingCategory(String s);

    List<ShoopingCategory> listWithTree(String recruitmentName);

    ShoppingRecruitmentName getId(Integer id);
}
