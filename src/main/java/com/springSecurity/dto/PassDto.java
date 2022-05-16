package com.springSecurity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PassDto {


    private Integer id;

    private String password;


        private String newPassword;

    private String confirmPassword;


}
