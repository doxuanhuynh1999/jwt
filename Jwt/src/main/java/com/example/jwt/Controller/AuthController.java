package com.example.jwt.Controller;

import com.example.jwt.Exception.ExistedException;
import com.example.jwt.dto.request.SignInForm;
import com.example.jwt.dto.request.SignUpForm;
import com.example.jwt.dto.response.JwtResponse;
import com.example.jwt.security.jwt.JwtProvider;
import com.example.jwt.security.userprincal.UserPrinciple;
import com.example.jwt.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequestMapping(value="/api/auth")
@RestController
public class AuthController {
    @Autowired
    private IUserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;
    @PostMapping(value = "/signup")
    public ResponseEntity<Object> signup(@RequestBody SignUpForm signUpForm) throws ExistedException {
        return new ResponseEntity<>(userService.save(signUpForm), HttpStatus.OK);
    }
    // tao token
    @PostMapping(value = "/signin")
    public ResponseEntity<Object> sign(@RequestBody SignInForm signInForm){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(signInForm.getUsername(),signInForm.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.createToken(authentication);
        UserPrinciple userPrinciple = (UserPrinciple) authentication.getPrincipal();
        return ResponseEntity.ok(new JwtResponse(token,userPrinciple.getName(), userPrinciple.getAuthorities()));
    }
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    @GetMapping(value = "/getALl")
    public ResponseEntity<Object> getAll(){
        return new ResponseEntity<>(userService.getAll(),HttpStatus.OK);
    }
    @PreAuthorize("hasAnyAuthority('ROLE_USER')")
    @PutMapping(value = "/update")
    public ResponseEntity<Object> update(@RequestParam("id") Long id, @RequestBody SignUpForm signUpForm){
        return new ResponseEntity<>(userService.update(id,signUpForm),HttpStatus.OK);
    }
    @DeleteMapping(value = "/delete")
    public ResponseEntity<Object> delete(@RequestParam("id") Long id){
        return new ResponseEntity<>(userService.delete(id),HttpStatus.OK);
    }
}
