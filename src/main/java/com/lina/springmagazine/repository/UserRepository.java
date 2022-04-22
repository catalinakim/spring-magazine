package com.lina.springmagazine.repository;

import com.lina.springmagazine.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    //Optional<Users> findByUserId(String user_id);
    Optional<Users> findByUsername(String username);
    Optional<Users> findByNickname(String nickname);
}
