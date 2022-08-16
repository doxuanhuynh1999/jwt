package com.example.jwt.repository;

import com.example.jwt.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IUserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String name); // tim kiem user co ton tai trong  DB khong
    Boolean existsByUsername(String username); // kiem tra xem username ton tai chua
    Boolean existsByEmail(String email); // kiem tra xem email co ton tai chua
}
