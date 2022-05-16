package com.springSecurity.mapper.product.productCategory;

import com.springSecurity.entity.ProductCategory;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ProductCategoryMapper {
    List<ProductCategory> getAll();

    List<ProductCategory> getById(Integer id);

    void delete(Integer id);

    void insert(ProductCategory productCategory);
}
