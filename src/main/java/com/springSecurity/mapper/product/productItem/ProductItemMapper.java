package com.springSecurity.mapper.product.productItem;

import com.springSecurity.dto.ProductItemDto;
import com.springSecurity.entity.ProductItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProductItemMapper {
    void insert(ProductItem productItem);

    List<ProductItemDto> getAll1(Map<String, Object> objectMap);

    void deletes(Map<String, Object> map);
}
