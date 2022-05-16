package com.springSecurity.service.product.ProductShopping.Impl;

import com.springSecurity.entity.ProductShopping;
import com.springSecurity.mapper.product.ProductShopping.ProductShoppingMapper;
import com.springSecurity.service.product.ProductShopping.ProductShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductShoppingServiceImpl implements ProductShoppingService {

    @Autowired
    private ProductShoppingMapper productShoppingMapper;


    @Override
    public void insert(ProductShopping productShopping) {

        productShoppingMapper.insert(productShopping);

    }

    @Override
    public ProductShopping getById(Integer id) {
        return productShoppingMapper.getById(id);
    }
}
