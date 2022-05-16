package com.springSecurity.service.product.product.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springSecurity.dto.ProductDto;
import com.springSecurity.entity.Product;
import com.springSecurity.from.SearchProductFrom;
import com.springSecurity.mapper.product.product.ProductMapper;
import com.springSecurity.service.product.product.ProductService;
import com.springSecurity.utils.ObjToMap;
import com.springSecurity.vo.UserRoleOrganization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;
    @Override
    public PageInfo<ProductDto> getPage(SearchProductFrom from, Pageable pageable) {


        Map<String, Object> objectMap = ObjToMap.getObjectMap(from);


        PageHelper.startPage(from.getPage(), from.getCount());

        List<ProductDto> productMapperPage=productMapper.getPage(objectMap);



        return new PageInfo<>(productMapperPage);
    }

    @Override
    public Product getByCategoryId(Integer id) {
        return productMapper.getByCategoryId(id);
    }

    @Override
    public List<Product> getByCategoryId1(Integer id) {
        return productMapper.getByCategoryId1(id);
    }

    @Override
    public void insert(Product product) {


        productMapper.insert(product);


    }

    @Override
    public Product getByProductName(String productName) {
        return productMapper.getByProductName(productName);
    }

    @Override
    public Product getByCategoryId2(Integer id) {
        return productMapper.getByCategoryId2(id);
    }

    @Override
    public void deletes(Map<String, Object> map) {

          productMapper.deletes(map);


    }

    @Override
    public Product getById(Integer productId) {
        return productMapper.getById(productId);
    }
}
