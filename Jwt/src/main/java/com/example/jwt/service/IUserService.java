package com.example.jwt.service;

import com.example.jwt.dto.request.SignUpForm;
import com.example.jwt.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<User> findByUsername(String name); // tim kiem user co ton tai trong  DB khong
//    Boolean existsByUserName(String username); // kiem tra xem username ton tai chua
//    Boolean existsByEmail(String email);
    User save(SignUpForm signUpForm);
    List<User> getAll();
    Boolean delete(Long id);
    User update(Long id, SignUpForm signUpForm);
}
