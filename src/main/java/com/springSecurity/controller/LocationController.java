package com.springSecurity.controller;



import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import com.springSecurity.dto.UserProvincesDto;
import com.springSecurity.entity.User;
import com.springSecurity.mapper.custom.LimitRequest;
import com.springSecurity.result.Result;
import com.springSecurity.service.user.UserService;
import com.springSecurity.utils.LocationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 高德经纬度
 */
@RestController
@RequestMapping("/location")
public class LocationController {

    /**日志对象*/
    private static final Logger logger = LoggerFactory.getLogger(LocationController.class);

    @Autowired
    private LocationUtils locationUtils;

    @Autowired
    private UserService userService;

    /**
     * 地理编码
     * http://localhost:2080/location/get?address=大同市平城区南环路&city=大同
     * @param address
     * @param city
     * @return
     */
    @GetMapping("/get")
    @LimitRequest(count=30)

    public JSONObject getLocation(@RequestParam String address, @RequestParam String city){

        Map<String, String> params = new HashMap<>();
        params.put("address", address);
        params.put("city", city);
        logger.info("获取地理编码,请求的参数为:{}", params);
        JSONObject map = locationUtils.getLocation(params);
        logger.info("获取地理编码,返回的请求结果为:{}", map);


        String[] test=map.getString("location").split(",");



      System.out.println(Arrays.toString(test));


        return map;
    }

    /**
     * 逆地理编码
     * http://localhost:2080/location/getCounter?location=113.300038,40.063278
     * @param location
     * @return
     */
    @GetMapping("/getCounter")
    @LimitRequest(count=30)

    public JSONObject getCounterLocation(@RequestParam String location){

        Map<String, String> params = new HashMap<>();
        params.put("location", location);
        logger.info("获取逆地理编码,请求的参数为:{}", params);
        JSONObject map = locationUtils.getCounterLocation(params);
        logger.info("获取逆地理编码,返回的请求结果为:{}", map);
        return map;
    }


    @GetMapping("/get1")
    @LimitRequest(count=30)

    public Result getLocation1(){

        List<User> list=userService.getAll();

        Map<String,Object> map=new HashMap<>();


        for(int i=0;i<list.size();i++){

            map.put(list.get(i).getProvinces(), JSON.parse(list.get(i).getLatLng()));
        }


        return Result.succ(200,"获取成功",map);

    }


    @GetMapping("/getProinces")
    @LimitRequest(count=30)

    public Result getProvinces(){

          List<UserProvincesDto> list=userService.getProinces();

        return Result.succ(200,"获取成功",list);

    }



}


