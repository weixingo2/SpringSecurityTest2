package com.springSecurity.service.organization;

import com.github.pagehelper.PageInfo;
import com.springSecurity.dto.OrgDto;
import com.springSecurity.entity.Organization;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface OrganizationService {
    PageInfo<Organization> getOrganizationAll(Organization organization, Pageable pageable);

    void insert(Organization organization, HttpServletRequest request);

    void delete(Integer id,HttpServletRequest request);

    Organization findRow(Integer id);

    void update(Organization organization,HttpServletRequest request);

    List<OrgDto> getEchart();

    List<Organization> getOrg();

    Organization getOrgId(Integer organizationId);
}
