package com.springSecurity.controller.log.UserLog;

import com.github.pagehelper.PageInfo;
import com.springSecurity.dto.UserDto;
import com.springSecurity.entity.UserLog;
import com.springSecurity.from.SearchFrom;
import com.springSecurity.mapper.custom.LimitRequest;
import com.springSecurity.result.Result;
import com.springSecurity.service.log.userLog.UserLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/sys/userLog")
public class UserLogController {


    @Autowired
    private UserLogService userLogService;


    //分页获取用户日志
    @PostMapping("/all")
    @LimitRequest(count=30)
    public Result getAll(@RequestBody SearchFrom from, Pageable pageable){

        PageInfo<UserLog> list=userLogService.getLogs(from,pageable);
        return Result.succ(200,"获取成功",list);

    }

    //批量删除
    @PostMapping("/delete")
    @LimitRequest(count=30)
    @PreAuthorize("hasAuthority('sys:log:delete')")
    public Result deleteIds(@RequestParam("ids") List<Integer> ids, HttpServletRequest request){

        userLogService.deleteIds(ids,request);

        return Result.succ(200,"删除成功",null);

    }


    /**
     * 获取用户当天登录的次数
     */

    @GetMapping("/count")
    public Result getCount(@RequestParam("created")String created){

        if(created.equals("")){

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

               created=formatter.format(new Date());

        }

        List<UserDto> list=userLogService.getAll(created);

        return Result.succ(200,"",list);
    }





}
