package com.springSecurity.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.springSecurity.dto.UserCountDto;
import com.springSecurity.entity.Organization;
import com.springSecurity.entity.Role;
import com.springSecurity.entity.User;
import com.springSecurity.entity.UserRole;
import com.springSecurity.from.UserSearchFrom;
import com.springSecurity.mapper.custom.LimitRequest;
import com.springSecurity.result.Result;
import com.springSecurity.service.UserRole.UserRoleService;
import com.springSecurity.service.organization.OrganizationService;
import com.springSecurity.service.role.RoleService;
import com.springSecurity.service.user.UserService;
import com.springSecurity.utils.LocationUtils;
import com.springSecurity.vo.UserRoleOrganization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;

import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/sys/user")
public class UserController {

    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public static final String SCORE_RANK = "score_rank";

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;


    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private LocationUtils locationUtils;


    @Autowired
    private RoleService roleService;

    @GetMapping("/Info/{id}")
    @LimitRequest(count=30)

    public Result getInfo(@PathVariable("id")Integer id){

        User user=userService.getInfo(id);



        //通过用户的id找到部门的id

        Organization organization=organizationService.getOrgId(user.getOrganizationId());


        user.setOrganizationName(organization.getOrganizationName());


        //根据用户的id查询
        List<UserRole> roles=userRoleService.getAll(id);


        //获取到当前的有关的roles后

        List<Role> list=new ArrayList<>();

        for(UserRole userRole:roles){

            Role role=roleService.getRole(userRole.getRoleId());

            list.add(role);
        }

        user.setRole(list);

        return Result.succ(user);

    }


    @PostMapping("/listPage")
    @LimitRequest(count=30)

    public Result getList(@RequestBody UserSearchFrom from, Pageable pageable, HttpServletRequest request){

        //获取当前用户的所有的数据
        PageInfo<UserRoleOrganization> users=userService.getAll1(from,pageable,request);
        return Result.succ(users);

    }


    @GetMapping("/list")
    @LimitRequest(count=30)

    public Result getList(){

        List<User> users=userService.getAll();
        return Result.succ(users);

    }








    @PostMapping("/save")
    @LimitRequest(count=30)

    @PreAuthorize("hasAuthority('sys:user:save')")
    public Result saveUser(@RequestBody User user,HttpServletRequest request){

        String password=passwordEncoder.encode("123456");

        String avatar="https://cube.elemecdn.com/0/88/03b0d39583f48206768a7534e55bcpng.png";

        user.setPassword(password);

        user.setAvatar(avatar);

        user.setOrganizationId(user.getOrganizationId());

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

        user.setCreated(formatter.format(new Date()));


        user.setProvinces(user.getProvinces());


        Map<String, String> params = new HashMap<>();
        params.put("address", user.getProvinces());

        params.put("city","");

        JSONObject map = locationUtils.getLocation(params);


        String[] test=map.getString("location").split(",");



        user.setLatLng(Arrays.toString(test));




        userService.saveUser(user,request);

        return Result.succ(user);

    }

    @PostMapping("/update")
    @LimitRequest(count=30)

    @PreAuthorize("hasAuthority('sys:user:update')")
    public Result updateUser(@RequestBody User user,HttpServletRequest request){

        user.setUpdated(new Date());

         user.setOrganizationId(user.getOrganizationId());
        userService.updateUser(user,request);

        return Result.succ(user);

    }


    @PostMapping("/delete")
    @Transactional
    @LimitRequest(count=30)

    @PreAuthorize("hasAuthority('sys:user:delete')")
    public Result delete(@RequestBody Integer[] ids,HttpServletRequest request){


        List<Integer> listIds= Arrays.asList(ids);

        Map<String,Object> map=new HashMap<>();

        map.put("listIds",listIds);

        userService.deleteIds(map,request);
        //删除中间的表
        userRoleService.delete(map);
        return Result.succ("删除成功");

    }


    @PostMapping("/role/{id}")
    @LimitRequest(count=30)

    @Transactional
    public Result rolePerm(@PathVariable("id")Integer id,@RequestBody Integer[] roleIds){

        List<UserRole> list=new ArrayList<>();

        Arrays.stream(roleIds).forEach(r->{
            UserRole userRole=new UserRole();

            userRole.setRoleId(r);
            userRole.setUserId(id);
            list.add(userRole);
        });

        userRoleService.deleteUserId(id);


        for(UserRole userRole:list){

            userRoleService.save(userRole);
        }

        return Result.succ("成功");

    }


    //获取用户统计
    @GetMapping("/get")
    @LimitRequest(count=30)


//    @Scheduled(fixedRate = 1000)
    public Result getCount(){


        List<UserCountDto> list=userService.getCount();




//        Set<ZSetOperations.TypedTuple<String>> tuples = new HashSet<>();
//
//        for(int i=0;i<list.size();i++){
//
//            DefaultTypedTuple<String> tuple = new DefaultTypedTuple<>(list.get(i).getProvinces(), 1D + list.get(i).getCount());
//            tuples.add(tuple);
//        }
//
//        redisTemplate.delete(SCORE_RANK);
//
//        Long num = redisTemplate.opsForZSet().add(SCORE_RANK, tuples);
//
//
//
//
//        Set<ZSetOperations.TypedTuple<String>> rangeWithScores = redisTemplate.opsForZSet().reverseRangeWithScores(SCORE_RANK, 0, 3);
//        System.out.println("获取到的排行列表:" + JSON.toJSONString(rangeWithScores));




        return Result.succ(200,"成功",list);


    }

    @PostMapping("/repass")
    @LimitRequest(count=30)

    @PreAuthorize("hasAuthority('sys:user:repass')")
    public Result repass(@RequestBody Integer userId,HttpServletRequest request){

        User user=userService.getInfo(userId);
        String password=passwordEncoder.encode("123456");

        user.setPassword(password);
        user.setUpdated(new Date());

        userService.updateUser(user,request);
        return Result.succ("修改成功");

    }




}
