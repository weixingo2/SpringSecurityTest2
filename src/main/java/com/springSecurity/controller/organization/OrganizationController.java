package com.springSecurity.controller.organization;

import com.github.pagehelper.PageInfo;
import com.springSecurity.dto.OrgDto;
import com.springSecurity.entity.Organization;
import com.springSecurity.from.UserSearchFrom;
import com.springSecurity.mapper.custom.LimitRequest;
import com.springSecurity.result.Result;
import com.springSecurity.service.organization.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/sys/org")
public class OrganizationController {

    @Autowired
    private OrganizationService organizationService;


    @PostMapping("/listPage")
    @LimitRequest(count=30)
    public Result getAll(@RequestBody Organization organization, Pageable pageable){


        PageInfo<Organization> list=organizationService.getOrganizationAll(organization,pageable);

        return Result.succ(list);

    }

    @PostMapping("/insert")
    @LimitRequest(count=30)
    @PreAuthorize("hasAuthority('sys:org:save')")
    public Result insert(@RequestBody Organization organization, HttpServletRequest request){


        organization.setCreated(new Date());

         organizationService.insert(organization,request);

        return Result.succ(200,"插入成功",null);

    }

    @GetMapping("/delete")
    @Transactional
    @LimitRequest(count=30)
    @PreAuthorize("hasAuthority('sys:org:delete')")
    public Result delete(@RequestParam("id")Integer id,HttpServletRequest request){
        organizationService.delete(id,request);
        return Result.succ(200,"删除成功",null);
    }


    @GetMapping("/findRow")
    @Transactional
    @LimitRequest(count=30)
    public Result fingRow(@RequestParam("id")Integer id){
       Organization organization= organizationService.findRow(id);
        return Result.succ(200,"获取成功",organization);
    }


    @PostMapping("/update")
    @LimitRequest(count=30)
    @PreAuthorize("hasAuthority('sys:org:update')")
    public Result update(@RequestBody Organization organization,HttpServletRequest request)
    {


        organization.setUpdated(new Date());

        organizationService.update(organization,request);

        return Result.succ(200,"更新成功",null);
    }


    @GetMapping("/getEchart")
    @LimitRequest(count=30)
    public Result getEchart(){

        List<OrgDto> list=organizationService.getEchart();

        return Result.succ(200,"成功",list);

    }


    @GetMapping("/get")
    @LimitRequest(count=30)
    public Result getAllOrg(){

        List<Organization> list=organizationService.getOrg();

        return Result.succ(200,"succ",list);
    }



}
