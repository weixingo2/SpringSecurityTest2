package com.springSecurity.from;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSearchFrom {



    private String name;

    private String phone;

    private String  organizationName;

    private String username;

    private String email;

    private Integer count;

    private Integer page;

    private String provinces;





}
