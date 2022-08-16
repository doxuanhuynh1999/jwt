package com.example.jwt.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users",uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
        // de cho email username phai khac nhau
})
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String username;
    private String email;
    @JsonIgnore
    private String password;
    private String avatar;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<Role> roles = new HashSet<Role>();
    public User() {
    }

    public User(Long id, String name, String username, String email, String password, String avatar, Set<Role> roles) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.avatar = avatar;
        this.roles = roles;
    }
}
