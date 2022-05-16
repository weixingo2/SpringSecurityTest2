package com.springSecurity.controller.product.product;

import com.github.pagehelper.PageInfo;
import com.springSecurity.dto.ProductDto;
import com.springSecurity.entity.Product;
import com.springSecurity.entity.ProductImg;
import com.springSecurity.entity.ProductParams;
import com.springSecurity.entity.ProductSku;
import com.springSecurity.from.ProductForm;
import com.springSecurity.from.SearchProductFrom;
import com.springSecurity.result.Result;
import com.springSecurity.service.product.ProductImg.ProductImgService;
import com.springSecurity.service.product.ProductParams.ProductParamsService;
import com.springSecurity.service.product.ProductSku.ProductSkuService;
import com.springSecurity.service.product.product.ProductService;
import com.springSecurity.vo.UserRoleOrganization;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/product")
public class ProductController {


    @Autowired
    private ProductService productService;


    @Autowired
    private ProductSkuService productSkuService;

    @Autowired
    private ProductParamsService productParamsService;

    @Autowired
    private ProductImgService productImgService;


    @PostMapping("/getPage")

    public Result getPage1(@RequestBody SearchProductFrom from, Pageable pageable){
        //获取当前用户的所有的数据
        PageInfo<ProductDto> users=productService.getPage(from,pageable);
        return Result.succ(users);

    }


    @GetMapping("/getId/{id}")
    @PreAuthorize("hasAuthority('sys:user:update')")
    public Result getProductSku(@PathVariable("id")Integer id){


        //通过分类的id获取到商品

        List<Product> productList=productService.getByCategoryId1(id);


        String untitled="";


        //通过商品找到sku

        HashSet<String> set = new HashSet<>();

          for(int i=0;i<productList.size();i++){

              ProductSku productSku=productSkuService.getByProductId(productList.get(i).getId());

             set.add(productSku.getUntitled());
          }


        String str = String.join(", ", set);


        return Result.succ(200,"获取成功",str);


    }

    @GetMapping("/getProductParams/{id}")
    @PreAuthorize("hasAuthority('sys:user:update')")
    public Result getProductParam(@PathVariable("id")Integer id){



        //通过分类的id获取到商品

        Product product=productService.getByCategoryId2(id);


        ProductParams productParams=productParamsService.getProductParam(product.getId());


        return Result.succ(200,"获取成功",productParams);
    }


    @PostMapping("/add")
    @PreAuthorize("hasAuthority('sys:user:update')")
    @Transactional
    public Result add(@RequestBody ProductForm from){
        //添加商品
        Product product=new Product();
        product.setProductName(from.getProductName());
        for(int i=0;i<from.getCategory().length;i++){
            product.setCategoryId(from.getCategory()[2]);
            product.setRootCategoryId(from.getCategory()[0]);
        }
        product.setContent(from.getContent());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        product.setCreated(formatter.format(new Date()));
        productService.insert(product);
        //通过商品名称获取
        Product product1=productService.getByProductName(from.getProductName());
        ProductImg productImg=new ProductImg();
        productImg.setProductId(product1.getId());
        productImg.setUrl(from.getUrl());
        productImg.setCreated(formatter.format(new Date()));
        productImgService.insert(productImg);
        ProductParams productParams=new ProductParams();
        productParams.setProductId(product1.getId());
        productParams.setProductPlace(from.getProductPlace());
        productParams.setBrand(from.getBrand());
        productParams.setFactoryAddress(from.getFactoryAddress());
        productParams.setFactoryName(from.getFactoryName());
        productParams.setWeight(from.getWeight());
        productParams.setCreated(formatter.format(new Date()));
        productParamsService.insert(productParams);
        ProductSku productSku=new ProductSku();
        productSku.setProductId(product1.getId());
        productSku.setCreated(formatter.format(new Date()));
        productSku.setUntitled(StringUtils.join(from.getManyTableData(), ","));
        productSku.setStock(from.getStock());
        productSku.setOriginalPrice(from.getOriginalPrice());
        productSkuService.insert(productSku);
        return Result.succ(200,"添加成功",null);

    }


    @PostMapping("/delete")
    @PreAuthorize("hasAuthority('sys:user:update')")
    @Transactional
    public Result deleteProduct(@RequestBody Integer[] ids){

        //通过商品的id删除相对应的数据
        List<Integer> proIds= Arrays.asList(ids);

        Map<String,Object> map=new HashMap<>();

        map.put("proIds",proIds);

        productService.deletes(map);

        //删除中间表
        productImgService.deleteIds(map);

        productParamsService.deleteIds(map);

        productSkuService.deleteIds(map);

        return Result.succ(200,"删除成功",null);

    }





}
