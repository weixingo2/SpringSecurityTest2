package com.springSecurity.service.product.productOrder;

import com.github.pagehelper.PageInfo;
import com.springSecurity.dto.ProductOrderDto;
import com.springSecurity.entity.ProductOrder;
import com.springSecurity.from.OrderFrom;
import org.springframework.data.domain.Pageable;

public interface ProductOrderService {
    PageInfo<ProductOrder> getAll1(OrderFrom from, Pageable pageable);

    void delete(Integer id);

    void insert(ProductOrderDto productOrderDto);

    ProductOrder getByUserId(Integer userId);
}
