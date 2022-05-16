package com.springSecurity.service.product.ProductShopping;

import com.springSecurity.entity.ProductShopping;

public interface ProductShoppingService {
    void insert(ProductShopping productShopping);

    ProductShopping getById(Integer id);
}
