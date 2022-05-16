package com.springSecurity.service.organization.Impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springSecurity.dto.OrgDto;
import com.springSecurity.entity.OperationLog;
import com.springSecurity.entity.Organization;
import com.springSecurity.mapper.organization.OrganizationMapper;
import com.springSecurity.service.log.operationLog.OperationLogService;
import com.springSecurity.service.organization.OrganizationService;
import com.springSecurity.utils.ObjToMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class OrganizationServiceImp implements OrganizationService {


    @Autowired
    private OrganizationMapper organizationMapper;

    @Autowired
    private OperationLogService operationLogService;

    @Override
    public PageInfo<Organization> getOrganizationAll(Organization organization, Pageable pageable) {

       Map<String,Object> objectMap= ObjToMap.getObjectMap(organization);


        PageHelper.startPage(organization.getPage(),organization.getCount());

       List<Organization> list=organizationMapper.getAll(objectMap);

        return new PageInfo<>(list);
    }

    @Override
    public void insert(Organization organization, HttpServletRequest request) {


        OperationLog operationLog=new OperationLog();

        operationLog.setName(request.getRemoteUser());
        operationLog.setCreated(new Date());
        operationLog.setIp(request.getRemoteAddr());

        operationLog.setOperation("保存部门数据");
        operationLogService.insert(operationLog);

        organizationMapper.insert(organization);

    }

    @Override
    public void delete(Integer id,HttpServletRequest request) {
        OperationLog operationLog=new OperationLog();

        operationLog.setName(request.getRemoteUser());
        operationLog.setCreated(new Date());
        operationLog.setIp(request.getRemoteAddr());

        operationLog.setOperation("删除部门数据");
        operationLogService.insert(operationLog);


        organizationMapper.delete(id);
    }

    @Override
    public Organization findRow(Integer id) {
        return organizationMapper.findRow(id);
    }

    @Override
    public void update(Organization organization,HttpServletRequest request) {

        OperationLog operationLog=new OperationLog();

        operationLog.setName(request.getRemoteUser());
        operationLog.setCreated(new Date());
        operationLog.setIp(request.getRemoteAddr());


         operationLog.setOperation("编辑权限数据");
         operationLogService.insert(operationLog);
         organizationMapper.update(organization);
    }

    @Override
    public List<OrgDto> getEchart() {
        return organizationMapper.getEchart();
    }

    @Override
    public List<Organization> getOrg() {
        return organizationMapper.getOrg();
    }

    @Override
    public Organization getOrgId(Integer organizationId) {
        return organizationMapper.getOrgId(organizationId);
    }
}
