package com.lina.springmagazine.security;

import com.lina.springmagazine.dto.LoginDto;
import com.lina.springmagazine.model.Users;
import com.lina.springmagazine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


//    public LoginDto findById(String username) {
//        System.out.println("in UserDetailsServiceImpl, username: " + username);
//        Users user = userRepository.findByUsername(username).orElseThrow(
//                () -> new IllegalArgumentException("아이디 혹은 비밀번호를 확인해주세요."));
//        return new LoginDto(user.getUsername(), user.getPassword());
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find " + username));
        System.out.println("in UserDetailsServiceImpl: " + user.getPassword());
        //return new LoginDto(user.getUsername(), user.getPassword());
        return new UserImpl(user);

    }

//    private Collection<? extends GrantedAuthority> authorities(AdminRole role) {
//        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + role.toString()));
//    }
}