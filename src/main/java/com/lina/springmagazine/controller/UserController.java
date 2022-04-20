package com.lina.springmagazine.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.lina.springmagazine.dto.UserDto;
import com.lina.springmagazine.model.ResponseMsg;
import com.lina.springmagazine.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

//@RequiredArgsConstructor
//@RestController
@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    // 회원 로그인 페이지
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // 회원 가입 요청 처리
    @PostMapping("/api/register")
    @ResponseBody
    public ResponseEntity registerUser(@RequestBody UserDto userDto) {
        ResponseMsg responseMsg;
        String msg = userService.registerUser(userDto);
        if (msg.equals("중복 ID")){
            responseMsg = new ResponseMsg("fail", "사용중인 ID");
            return new ResponseEntity(responseMsg, HttpStatus.BAD_REQUEST);

        }else if (msg.equals("중복 닉네임")){
            //return new ResponseMsg(HttpStatus.BAD_REQUEST, "fail", "사용중인 ID");
            //return new ResponseMsg("fail", "사용중인 ID");
            responseMsg = new ResponseMsg("fail", "사용중인 닉네임");
            return new ResponseEntity(responseMsg, HttpStatus.BAD_REQUEST);

        }else if (msg.equals("닉네임 조건 불일치")){
            responseMsg = new ResponseMsg("fail", "닉네임은 최소 3자 이상, 알파벳 대소문자, 숫자로 구성 가능");
            return new ResponseEntity(responseMsg, HttpStatus.BAD_REQUEST);

        }else if (msg.equals("비밀번호 조건 불일치")){
            responseMsg = new ResponseMsg("fail", "비밀번호는 최소 4자 이상, 닉네임 포함 불가");
            return new ResponseEntity(responseMsg, HttpStatus.BAD_REQUEST);
        }

        responseMsg = new ResponseMsg("success", "회원가입 성공");

        return new ResponseEntity(responseMsg, HttpStatus.OK);
    }

//    @GetMapping("/api/login")
//    @ResponseBody
//    public ResponseEntity login(@RequestParam String nickname, String password) {
//        ResponseMsg responseMsg;
//        userService.login(nickname, password);
//
//        responseMsg = new ResponseMsg("success", "로그인 성공");
//        return new ResponseEntity(responseMsg, HttpStatus.OK);
//    }
}
