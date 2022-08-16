package com.example.jwt.config;

import com.example.jwt.security.jwt.JwtEntryPoint;
import com.example.jwt.security.jwt.JwtTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = false, jsr250Enabled = false)
public class WebSecurityConfig {
    @Autowired
    private JwtEntryPoint jwtEntryPoint;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests()
                .antMatchers("/api/auth/**").permitAll()
                //.antMatchers("/api/auth/getALl").hasAnyAuthority("ROLE_ADMIN","ROLE_USER")
                //.antMatchers("/api/auth/signup").permitAll()
                //.antMatchers("/api/auth/update").hasAnyAuthority("ROLE_ADMIN")
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .httpBasic();
        http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

}
