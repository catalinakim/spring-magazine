package com.lina.springmagazine.security;

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

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByNickname(username)
                .orElseThrow(() -> new UsernameNotFoundException("Can't find " + username));
        System.out.println("in UserDetailsServiceImpl:" + user.getNickname());
        return new UserImpl(user);
    }
}