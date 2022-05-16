package com.springSecurity.service.product.productOrder.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springSecurity.dto.ProductOrderDto;
import com.springSecurity.entity.ProductOrder;
import com.springSecurity.from.OrderFrom;
import com.springSecurity.mapper.product.productOrder.ProductOrderMapper;
import com.springSecurity.service.product.productOrder.ProductOrderService;
import com.springSecurity.utils.ObjToMap;
import com.springSecurity.vo.UserRoleOrganization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductOrderServiceImpl implements ProductOrderService {
    @Autowired
    private ProductOrderMapper productOrderMapper;
    @Override
    public PageInfo<ProductOrder> getAll1(OrderFrom from, Pageable pageable) {
        Map<String, Object> objectMap = ObjToMap.getObjectMap(from);


        PageHelper.startPage(from.getPage(), from.getCount());

        List<ProductOrder> orderList=productOrderMapper.getAll1(objectMap);

        return new PageInfo<>(orderList);

    }

    @Override
    public void delete(Integer id) {
        productOrderMapper.delete(id);
    }

    @Override
    public void insert(ProductOrderDto productOrderDto) {
          productOrderMapper.insert(productOrderDto);
    }

    @Override
    public ProductOrder getByUserId(Integer userId) {
        return productOrderMapper.getByUserId(userId);
    }
}
