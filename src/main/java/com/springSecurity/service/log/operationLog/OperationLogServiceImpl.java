package com.springSecurity.service.log.operationLog;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springSecurity.entity.OperationLog;
import com.springSecurity.from.OperationLogFrom;
import com.springSecurity.mapper.log.OperationLog.OperationLogMapper;
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
public class OperationLogServiceImpl implements OperationLogService{

    @Autowired
    private OperationLogMapper operationLogMapper;

    @Autowired
    private OperationLogService operationLogService;


    @Override
    public PageInfo<OperationLog> getAllList(OperationLogFrom from, Pageable pageable) {

        Map<String,Object> objectMap= ObjToMap.getObjectMap(from);

        PageHelper.startPage(from.getPage(),from.getCount());

        List<OperationLog> list=operationLogMapper.getAll(objectMap);


        return new PageInfo<>(list);
    }

    @Override
    public void insert(OperationLog operationLog) {
        operationLogMapper.insert(operationLog);
    }

    @Override
    public void deleteIds(List<Integer> ids, HttpServletRequest request) {

        Map<String,Object>objectMap=new HashMap<>();

        objectMap.put("ids",ids);

        OperationLog operationLog=new OperationLog();

        operationLog.setName(request.getRemoteUser());
        operationLog.setCreated(new Date());
        operationLog.setIp(request.getRemoteAddr());

        operationLog.setOperation("删除操作日志数据");
        operationLogService.insert(operationLog);


        operationLogMapper.deleteIds(objectMap);
    }
}
