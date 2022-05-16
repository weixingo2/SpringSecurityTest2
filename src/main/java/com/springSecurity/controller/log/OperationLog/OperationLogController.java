package com.springSecurity.controller.log.OperationLog;

import com.github.pagehelper.PageInfo;
import com.springSecurity.entity.OperationLog;
import com.springSecurity.from.OperationLogFrom;
import com.springSecurity.mapper.custom.LimitRequest;
import com.springSecurity.result.Result;
import com.springSecurity.service.log.operationLog.OperationLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/sys/opeLog")
public class OperationLogController {


    @Autowired
    private OperationLogService operationLogService;


    //分页获取操作日志
    @PostMapping("/all")
    @LimitRequest(count=30)
    public Result getAll(@RequestBody OperationLogFrom from, Pageable pageable){


        PageInfo<OperationLog> list=operationLogService.getAllList(from,pageable);

        return Result.succ(200,"获取成功",list);


    }



    //批量删除
    @PostMapping("/delete")
    @LimitRequest(count=30)
    @PreAuthorize("hasAuthority('sys:log:delete')")
    public Result deleteIds(@RequestParam("ids") List<Integer> ids, HttpServletRequest request){

        operationLogService.deleteIds(ids,request);

        return Result.succ(200,"删除成功",null);

    }



}
