package com.springSecurity.service.product.ProductImg.Impl;

import com.springSecurity.entity.ProductImg;
import com.springSecurity.mapper.product.productImg.ProductImgMapper;
import com.springSecurity.service.product.ProductImg.ProductImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProductImgServuceImpl implements ProductImgService {

    @Autowired
    private ProductImgMapper productImgMapper;

    @Override
    public void insert(ProductImg productImg) {
            productImgMapper.insert(productImg);
    }

    @Override
    public void deleteIds(Map<String, Object> map) {

        productImgMapper.deleteIds(map);
    }
}
