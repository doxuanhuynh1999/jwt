package com.example.jwt.service.ipm;

import com.example.jwt.Exception.ExistedException;
import com.example.jwt.Exception.NotFoundException;
import com.example.jwt.dto.request.SignUpForm;
import com.example.jwt.model.Role;
import com.example.jwt.model.RoleName;
import com.example.jwt.model.User;
import com.example.jwt.repository.IUserRepository;
import com.example.jwt.service.IRoleService;
import com.example.jwt.service.IUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceIpm implements IUserService {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private IRoleService roleService;;
    @Override
    public Optional<User> findByUsername(String name) {
        return userRepository.findByUsername(name);
    }
//
//    @Override
//    public Boolean existsByUserName(String username) {
//        return userRepository.existsByUserName(username);
//    }
//
//    @Override
//    public Boolean existsByEmail(String email) {
//        return userRepository.existsByEmail(email);
//    }
    @Override
    public User save(SignUpForm signUpForm) {
        if( userRepository.existsByUsername(signUpForm.getUsername()) == true) {
            throw new ExistedException("Existed User name");
        }
        if (userRepository.existsByEmail(signUpForm.getEmail() ) == true){
            throw  new ExistedException("Existed Email");
        }
        User user = new User();
        BeanUtils.copyProperties(signUpForm,user);
        user.setPassword(passwordEncoder.encode(signUpForm.getPassword()));
        Set<String> strRoles = signUpForm.getRoles();
        Set<Role> roles = new HashSet<>();
        strRoles.forEach(role -> {
            switch (role){
                case "admin":
                    Role adminRole = roleService.findByName(RoleName.ADMIN).orElseThrow(
                            () -> new RuntimeException("Role not found")
                    );
                    roles.add(adminRole);
                    break;
                case "pm":
                    Role pmRole = roleService.findByName(RoleName.PM).orElseThrow(
                            () -> new RuntimeException("Role not found")
                    );
                    roles.add(pmRole);
                    break;
                default:
                    Role userRole = roleService.findByName(RoleName.USER).orElseThrow(
                            () -> new RuntimeException("Role not found")
                    );
                    roles.add(userRole);
            }
        });
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public Boolean delete(Long id) {
        userRepository.deleteById(id);
        return true;
    }

    @Override
    public User update(Long id, SignUpForm signUpForm) {
        User user1 = userRepository.findById(id).stream().findFirst().orElse(null);
        if(user1 == null) {
            throw new NotFoundException("not found user");
        }
        User user = new User();
        BeanUtils.copyProperties(signUpForm,user);
        user.setId(id);
        user.setPassword(passwordEncoder.encode(signUpForm.getPassword()));
        Set<String> strRoles = signUpForm.getRoles();
        Set<Role> roles = new HashSet<>();
        strRoles.forEach(role -> {
            switch (role){
                case "admin":
                    Role adminRole = roleService.findByName(RoleName.ADMIN).orElseThrow(
                            () -> new RuntimeException("Role not found")
                    );
                    roles.add(adminRole);
                    break;
                case "pm":
                    Role pmRole = roleService.findByName(RoleName.PM).orElseThrow(
                            () -> new RuntimeException("Role not found")
                    );
                    roles.add(pmRole);
                    break;
                default:
                    Role userRole = roleService.findByName(RoleName.USER).orElseThrow(
                            () -> new RuntimeException("Role not found")
                    );
                    roles.add(userRole);
            }
        });
        user.setRoles(roles);
        return userRepository.save(user);
    }
}
