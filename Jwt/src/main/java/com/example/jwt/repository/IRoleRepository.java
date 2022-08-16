package com.example.jwt.repository;

import com.example.jwt.model.Role;
import com.example.jwt.model.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName name);

}
