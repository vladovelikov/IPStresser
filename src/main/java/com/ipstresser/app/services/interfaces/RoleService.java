package com.ipstresser.app.services.interfaces;

import com.ipstresser.app.domain.entities.Role;

import java.util.Collection;
import java.util.List;

public interface RoleService {

    Role getRoleByName(String role);

    List<Role> getAllRoles();
}
