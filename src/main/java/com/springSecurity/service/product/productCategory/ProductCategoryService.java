package com.springSecurity.service.product.productCategory;

import com.springSecurity.entity.ProductCategory;

import java.util.List;

public interface ProductCategoryService {
    List<ProductCategory> listWithTree();

    void delete(Integer id);

    List<ProductCategory> getById(Integer id);


    void insert(ProductCategory productCategory);
}
