package com.springSecurity.controller.product.ProductCategory;

import com.springSecurity.entity.Product;
import com.springSecurity.entity.ProductCategory;
import com.springSecurity.from.CategoryForm;
import com.springSecurity.result.Result;
import com.springSecurity.service.product.productCategory.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/productCategory")
public class ProductCategoryController {

    @Autowired
    private ProductCategoryService productCategoryService;



    @GetMapping("/get")

    public Result getProductCategory(){

        List<ProductCategory> categoryEntityList= productCategoryService.listWithTree();


        return Result.succ(200,"获取成功",categoryEntityList);
    }


    @GetMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('sys:user:update')")
   public Result delete(@PathVariable("id")Integer id){

        //先查询分类

        List <ProductCategory> productCategory=productCategoryService.getById(id);

        if(productCategory.size()>0){

            return Result.succ(200,"有子级不能删除",null);

        }

        productCategoryService.delete(id);

        return Result.succ(200,"删除成功",null);

    }


    //添加分类
    @PostMapping("/add")
    @PreAuthorize("hasAuthority('sys:user:update')")
    public Result addCategory(@RequestBody CategoryForm from){


        ProductCategory productCategory=new ProductCategory();

        productCategory.setParentId(from.getCid());

        productCategory.setCategoryName(from.getCategoryName());

        productCategory.setStatu(1);


      productCategoryService.insert(productCategory);


        return Result.succ(200,"添加成功",null);

    }



}
