package com.example.jwt.dto.request;

import lombok.Data;

@Data
public class SignInForm {
    private String username;
    private String password;

    public SignInForm(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public SignInForm(){};
}
