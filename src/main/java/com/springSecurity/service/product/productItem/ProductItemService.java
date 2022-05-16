package com.springSecurity.service.product.productItem;

import com.github.pagehelper.PageInfo;
import com.springSecurity.dto.ProductItemDto;
import com.springSecurity.entity.ProductItem;
import com.springSecurity.from.ItemFrom;
import org.springframework.data.domain.Pageable;

import java.util.Map;

public interface ProductItemService {
    void insert(ProductItem productItem);

    PageInfo<ProductItemDto> getPage(ItemFrom from, Pageable pageable);

    void deletes(Map<String, Object> map);
}
