package com.springSecurity.service.product.productCategory.Impl;

import com.springSecurity.entity.ProductCategory;
import com.springSecurity.mapper.product.productCategory.ProductCategoryMapper;
import com.springSecurity.service.product.productCategory.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Override
    public List<ProductCategory> listWithTree() {

         //查询所有的分类商品
        List<ProductCategory> list=productCategoryMapper.getAll();

        //2.1 找到所有的一级分类
        List<ProductCategory> level1Menus = list.stream().filter(categoryEntity ->
                categoryEntity.getParentId() == 0

        ).map((menu)->{
            menu.setChildren(getChildrens(menu, list));
            return menu;
        }).sorted((menu1, menu2)->{
            return (menu1.getCategoryLevel() == null ? 0 : menu1.getCategoryLevel()) - (menu2.getCategoryLevel() == null ? 0 : menu2.getCategoryLevel());
        }).collect(Collectors.toList());


        return level1Menus;

    }

    @Override
    public void delete(Integer id) {

        productCategoryMapper.delete(id);

    }

    @Override
    public List<ProductCategory> getById(Integer id) {
        return productCategoryMapper.getById(id);
    }

    @Override
    public void insert(ProductCategory productCategory) {

         productCategoryMapper.insert(productCategory);
    }


    private List<ProductCategory> getChildrens(ProductCategory root, List<ProductCategory> all){
        List<ProductCategory> childrean =all.stream().filter(categoryEntity -> {
            return categoryEntity.getParentId() == root.getId();
        }).map(categoryEntity -> {
            //找到子菜单
            categoryEntity.setChildren(getChildrens(categoryEntity, all));

            return categoryEntity;
        }).sorted((menu1, menu2)->{
            //2.菜单排序
            return (menu1.getCategoryLevel() == null ? 0 : menu1.getCategoryLevel()) - (menu2.getCategoryLevel() == null ? 0 : menu2.getCategoryLevel());
        }).collect(Collectors.toList());
        return childrean;

    }



}
