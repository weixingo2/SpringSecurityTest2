package com.springSecurity.mapper.organization;

import com.springSecurity.dto.OrgDto;
import com.springSecurity.entity.Organization;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface OrganizationMapper {

    List<Organization> getAll( Map<String,Object> objectMap);


    void insert(Organization organization);

    @Delete("delete o.* from organization o where o.id=#{id}")
    void delete(Integer id);

    @Select("select o.* from organization o where o.id=#{id}")
    Organization findRow(Integer id);

    void update(Organization organization);

    List<OrgDto> getEchart();

    List<Organization> getOrg();

    @Select("select o.* from organization o where o.id=#{organization}")
    Organization getOrgId(Integer organizationId);
}
