package com.example.todolist.User;


import com.example.todolist.todolist.TodoList;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
@Entity
@Table(name = "_user") //since user table is reserved in postgresql
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true, nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;

    //EnumType.Ordinal will convert the role to numbers like 0,1,2 while
    //EnumType.String will save it as a string
    @Enumerated(EnumType.STRING)
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //return list of roles
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        //function to check whether the account is not expired
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        //check whether the account is not locked
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        //check whether the credentials are not expired
        return true;
    }

    @Override
    public boolean isEnabled() {
        //function to check whether the user is enabled.
        return true;
    }

}
