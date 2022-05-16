package com.springSecurity.controller.ShoppingSliders;

import com.github.pagehelper.PageInfo;
import com.springSecurity.entity.*;
import com.springSecurity.result.Result;
import com.springSecurity.service.ShopingSliders.ShopingSlidersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shop")
public class ShoppingSlidersController {


    @Autowired
    private ShopingSlidersService shopingSlidersService;



    @GetMapping("/getSliders")
    public Result getAll(){

     List<ShopingSliders> list=shopingSlidersService.getList();

     return Result.succ(200,"成功",list);
    }


    @GetMapping("/getIcon")
    public Result getAllIcon(){

        List<ShoppingIcon> list=shopingSlidersService.getListIcon();

        return Result.succ(200,"成功",list);
    }


    @PostMapping("/getRecuritment")
    public Result getShoppingRecuritment(@RequestBody RecuritmentFrom from){

        PageInfo<ShoppingRecruitmentName> list=shopingSlidersService.geTAll(from);

        return Result.succ(200,"成功",list);

    }


    @GetMapping("/get")

    public Result getProductCategory(@RequestParam("recruitmentName") String recruitmentName){

        List<ShoopingCategory> categoryEntityList= shopingSlidersService.listWithTree(recruitmentName);


        return Result.succ(200,"获取成功",categoryEntityList);
    }


    @GetMapping("/getId")
    public Result getShoopingRecuritmentId(@RequestParam("id")Integer id){


        ShoppingRecruitmentName shoppingRecruitmentName=shopingSlidersService.getId(id);

        return Result.succ(200,"成功",shoppingRecruitmentName);
    }


}
