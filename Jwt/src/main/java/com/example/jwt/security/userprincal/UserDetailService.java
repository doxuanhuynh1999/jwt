package com.example.jwt.security.userprincal;

import com.example.jwt.model.User;
import com.example.jwt.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailService implements UserDetailsService {
    // ham nay kiem tra xem user co ton tai tren user khong
    @Autowired
    IUserRepository userRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  userRepository.findByUsername(username).stream().findFirst().orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return UserPrinciple.build(user);
    }
}
