package com.springSecurity.from;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationLogFrom {

    private Integer page;

    private Integer count;

    private String ip;

    private String name;
}
