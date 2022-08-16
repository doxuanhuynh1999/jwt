package com.example.jwt.service.ipm;

import com.example.jwt.model.Role;
import com.example.jwt.model.RoleName;
import com.example.jwt.repository.IRoleRepository;
import com.example.jwt.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleServiceIpm implements IRoleService {
    @Autowired
    IRoleRepository iRoleRepository;

    @Override
    public Optional<Role> findByName(RoleName name) {
        return iRoleRepository.findByName(name);
    }
}
