package com.springSecurity.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.lucene.spatial3d.geom.SerializableObject;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleMenu implements Serializable {

    private Integer id;

    private Integer roleId;

    private Integer menuId;


}
