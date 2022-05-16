package com.springSecurity.service.product.ProductParams.Impl;

import com.springSecurity.entity.ProductParams;
import com.springSecurity.mapper.product.productParams.ProductParamsMapper;
import com.springSecurity.service.product.ProductParams.ProductParamsService;
import com.springSecurity.service.product.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProductParamsServiceImpl implements ProductParamsService {

    @Autowired
    private ProductParamsMapper productParamsMapper;

    @Override
    public ProductParams getProductParam(Integer id) {
        return productParamsMapper.getProductParam(id);
    }

    @Override
    public void insert(ProductParams productParams) {

        productParamsMapper.insert(productParams);

    }

    @Override
    public void deleteIds(Map<String, Object> map) {
         productParamsMapper.deleteIds(map);
    }
}
