package com.springSecurity.service.product.product;

import com.github.pagehelper.PageInfo;
import com.springSecurity.dto.ProductDto;
import com.springSecurity.entity.Product;
import com.springSecurity.from.SearchProductFrom;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ProductService {
    PageInfo<ProductDto> getPage(SearchProductFrom from, Pageable pageable);

   Product getByCategoryId(Integer id);

    List<Product> getByCategoryId1(Integer id);

    void insert(Product product);

    Product getByProductName(String productName);


    Product getByCategoryId2(Integer id);

    void deletes(Map<String, Object> map);

    Product getById(Integer productId);
}
