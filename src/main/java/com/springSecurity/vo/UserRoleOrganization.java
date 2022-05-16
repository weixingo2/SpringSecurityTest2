package com.springSecurity.vo;

import com.springSecurity.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleOrganization extends User {


    private String name;


   private String remark;

   private String organizationName;

}
