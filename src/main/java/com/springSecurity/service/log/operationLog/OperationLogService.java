package com.springSecurity.service.log.operationLog;

import com.github.pagehelper.PageInfo;
import com.springSecurity.entity.OperationLog;
import com.springSecurity.from.OperationLogFrom;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OperationLogService {
    PageInfo<OperationLog> getAllList(OperationLogFrom from, Pageable pageable);

    void insert(OperationLog operationLog);

    void deleteIds(List<Integer> ids, HttpServletRequest request);
}
