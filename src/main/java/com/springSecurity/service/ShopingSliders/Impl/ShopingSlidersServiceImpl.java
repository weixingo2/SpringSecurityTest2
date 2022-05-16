package com.springSecurity.service.ShopingSliders.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springSecurity.entity.*;
import com.springSecurity.mapper.product.ShopingSliders.ShopingSlidersMapper;
import com.springSecurity.service.ShopingSliders.ShopingSlidersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ShopingSlidersServiceImpl implements ShopingSlidersService {

    @Autowired
    private ShopingSlidersMapper shopingSlidersMapper;

    @Override
    public void insert(String url) {

        shopingSlidersMapper.insert(url);
    }

    @Override
    public List<ShopingSliders> getList() {
        return shopingSlidersMapper.getList();
    }

    @Override
    public void insertIcon(String iconPath) {
        shopingSlidersMapper.insertIcon(iconPath);
    }

    @Override
    public List<ShoppingIcon> getListIcon() {
        return shopingSlidersMapper.getListIcon();
    }

    @Override
    public void insertShoppingRecruit(String url) {
        shopingSlidersMapper.insertShoppingRecruit(url);
    }

    @Override
    public PageInfo<ShoppingRecruitmentName> geTAll(RecuritmentFrom from) {


        PageHelper.startPage(from.getPage(),from.getCount());
        List<ShoppingRecruitmentName> list=shopingSlidersMapper.getAll();

        return new PageInfo<>(list);
    }

    @Override
    public void insertShoppingCategory(String url) {

        shopingSlidersMapper.insertShoppingCategory(url);
    }

    @Override
    public List<ShoopingCategory> listWithTree(String recruitmentName) {
        //查询所有的分类商品
        List<ShoopingCategory> list=shopingSlidersMapper.getCategoryList(recruitmentName);

        //2.1 找到所有的一级分类
        List<ShoopingCategory> level1Menus = list.stream().filter(categoryEntity ->
                categoryEntity.getParentId() == 0

        ).map((menu)->{
            menu.setChildren(getChildrens(menu, list));
            return menu;
        }).sorted((menu1, menu2)->{
            return (menu1.getLevel() == null ? 0 : menu1.getLevel()) - (menu2.getLevel() == null ? 0 : menu2.getLevel());
        }).collect(Collectors.toList());


        return level1Menus;
    }

    @Override
    public ShoppingRecruitmentName getId(Integer id) {
        return shopingSlidersMapper.getId(id);
    }

    private List<ShoopingCategory> getChildrens(ShoopingCategory root, List<ShoopingCategory> all){
        List<ShoopingCategory> childrean =all.stream().filter(categoryEntity -> {
            return categoryEntity.getParentId() == root.getId();
        }).map(categoryEntity -> {
            //找到子菜单
            categoryEntity.setChildren(getChildrens(categoryEntity, all));

            return categoryEntity;
        }).sorted((menu1, menu2)->{
            //2.菜单排序
            return (menu1.getLevel() == null ? 0 : menu1.getLevel()) - (menu2.getLevel() == null ? 0 : menu2.getLevel());
        }).collect(Collectors.toList());
        return childrean;

    }

}
