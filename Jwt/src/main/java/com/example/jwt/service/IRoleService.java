package com.example.jwt.service;

import com.example.jwt.model.Role;
import com.example.jwt.model.RoleName;

import java.util.Optional;

public interface IRoleService {
    Optional<Role> findByName(RoleName name);
}
