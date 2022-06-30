package com.ipstresser.app.services;

import com.ipstresser.app.domain.entities.Role;
import com.ipstresser.app.repositories.RoleRepository;
import com.ipstresser.app.services.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role getRoleByName(String name) {
        return this.roleRepository.findRoleByName(name);
    }

    @Override
    public List<Role> getAllRoles() {
        return this.roleRepository.findAll();
    }
}
