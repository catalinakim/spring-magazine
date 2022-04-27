package com.lina.springmagazine.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//@Getter
//@Setter
//public class LoginDto {
//    private String username;
//    private String password;
//
//    public LoginDto(String username, String password) {
//        this.username = username;
//        this.password = password;
//    }
//}

@Getter
@Setter
@RequiredArgsConstructor
public class LoginDto implements UserDetails {
    private String username;
//    @JsonIgnore
    private String password;
    private String tokenId;

    public LoginDto(String username, String password) {

        this.username = username;
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}