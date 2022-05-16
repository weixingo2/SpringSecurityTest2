package com.springSecurity.mapper.product.ProductShopping;

import com.springSecurity.entity.ProductShopping;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ProductShoppingMapper {

    void insert(ProductShopping productShopping);

    @Select("select ps.* from product_shopping ps where ps.id=#{id}")
    ProductShopping getById(Integer id);
}
