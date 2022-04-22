package com.lina.springmagazine.security;

import com.lina.springmagazine.model.UserRoleEnum;
import com.lina.springmagazine.model.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class UserImpl implements UserDetails {

    private final Users user;

    public UserImpl(Users user) {
        System.out.println("UserImpl 생성:" + user.getNickname());
        this.user = user;
    }

    public Users getUser() {
        return user;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getNickname();
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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //return Collections.emptyList();
        System.out.println("in UserImpl, getAuthorities()");

        // 로그인된 user 의 권한을 가져와서
        // 걔를 스프링 시큐리티의 규칙에 맞춰서 보내주기
//        UserRoleEnum userRole = user.getRole();
//        String authority = userRole.getAuthority();  //"ROLE_USER" or "ROLE_ADMIN"
//
//        // Collection 거쳐서 보내주기
//        // Collection 을 만들고 add
//        SimpleGrantedAuthority simpleAuthority = new SimpleGrantedAuthority(authority);
//        Collection<GrantedAuthority> authorities = new ArrayList<>(); //여러개가 될 수 있어서 복수형태
//        authorities.add(simpleAuthority);

        //https://zest133.tistory.com/entry/Spring-Security-login2 [제스트의 블로그]
        List<GrantedAuthority> authorities = new ArrayList<>();
        //authorities.add(new SimpleGrantedAuthority("USER"));
        return authorities;
    }
}
