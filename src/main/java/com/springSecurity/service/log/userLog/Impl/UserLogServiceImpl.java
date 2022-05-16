package com.springSecurity.service.log.userLog.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springSecurity.dto.UserDto;
import com.springSecurity.entity.OperationLog;
import com.springSecurity.entity.UserLog;
import com.springSecurity.from.SearchFrom;
import com.springSecurity.mapper.log.UserLog.UserLogMapper;
import com.springSecurity.service.log.userLog.UserLogService;
import com.springSecurity.utils.ObjToMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserLogServiceImpl implements UserLogService {

    @Autowired
    private UserLogMapper userLogMapper;
    @Override
    public PageInfo<UserLog> getLogs(SearchFrom from, Pageable pageable) {


        Map<String,Object> objectMap= ObjToMap.getObjectMap(from);

        PageHelper.startPage(from.getPage(),from.getCount());

        List<UserLog> list=userLogMapper.getLogs(objectMap);


        return new PageInfo<>(list);
    }

    @Override
    public void insert(UserLog userLog) {

        userLogMapper.insert(userLog);
    }

    @Override
    public void deleteIds(List<Integer> ids, HttpServletRequest request) {

        Map<String,Object>objectMap=new HashMap<>();

        objectMap.put("ids",ids);

        OperationLog operationLog=new OperationLog();

        operationLog.setName(request.getRemoteUser());
        operationLog.setCreated(new Date());
        operationLog.setIp(request.getRemoteAddr());

        operationLog.setOperation("删除用户登入日志数据");

        userLogMapper.deleteIds(objectMap);



    }

    @Override
    public List<UserDto> getAll(String created) {
        return userLogMapper.getAll(created);
    }
}
