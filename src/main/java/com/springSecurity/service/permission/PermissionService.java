package com.springSecurity.service.permission;

import java.util.List;

public interface PermissionService {
    List<Integer> getNavMenuIds(Integer userId);
}
