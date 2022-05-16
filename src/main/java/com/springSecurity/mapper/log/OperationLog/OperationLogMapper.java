package com.springSecurity.mapper.log.OperationLog;

import com.springSecurity.entity.OperationLog;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface OperationLogMapper {
    List<OperationLog> getAll(Map<String, Object> objectMap);

    void insert(OperationLog operationLog);

    void deleteIds(Map<String, Object> objectMap);
}
