package com.springSecurity.mapper.product.product;

import com.springSecurity.dto.ProductDto;
import com.springSecurity.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductMapper {
    List<ProductDto> getPage(Map<String, Object> objectMap);

   Product getByCategoryId(Integer id);

    List<Product> getByCategoryId1(Integer id);

    void insert(Product product);

    @Select("select p.* from product p where p.productName=#{productName}")
    Product getByProductName(String productName);

    Product getByCategoryId2(Integer id);

    void deletes(Map<String, Object> map);

    Product getById(Integer productId);
}
