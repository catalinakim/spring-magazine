package com.lina.springmagazine.service;

import com.lina.springmagazine.dto.UserDto;
import com.lina.springmagazine.model.UserRoleEnum;
import com.lina.springmagazine.model.Users;
import com.lina.springmagazine.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String registerUser(@RequestBody UserDto userDto){
        String username = userDto.getUsername();
        String nickname = userDto.getNickname();
        String email = userDto.getEmail();
        String password = userDto.getPassword();

        // 회원 ID 중복 확인
        //Optional<Users> foundId = userRepository.findByUserId(user_id);
        Optional<Users> foundId = userRepository.findByUsername(username);
        if (foundId.isPresent()){
            //throw new IllegalArgumentException("사용중인 ID입니다.");
            return "중복 ID";
        }

        // 회원 닉네임 중복 확인
        Optional<Users> foundNick = userRepository.findByNickname(nickname);
        if (foundNick.isPresent()){
            return "중복 닉네임";
        }

        // 닉네임 조건 확인
        boolean nicknameOk = false;
        if (nickname.length() >= 3 && Pattern.matches("^[a-zA-Z0-9]*$", nickname)){
            nicknameOk = true;
        }
        if (nicknameOk == false){
            return "닉네임 조건 불일치";
        }

        // 비밀번호 조건 확인
        boolean passwordOk = false;
        if (password.length() >= 4 && !password.contains(nickname)){
            passwordOk = true;
        }
        if (passwordOk == false){
            return "비밀번호 조건 불일치";
        }

        password = passwordEncoder.encode(password);
        userDto.setPassword(password);

        //UserRoleEnum role = UserRoleEnum.USER;
        //Users user = new Users(userDto, role);
        Users user = new Users(userDto);

        userRepository.save(user);

        //return user.getUser_no();
        //return "{'result' : 'success', 'msg': '회원가입 성공'}";  //문자열로 리턴됨
        return "회원가입 성공";
    }


//    public void login(String nickname, String password) {
//
//    }
}
