package com.lina.springmagazine.controller;

import com.lina.springmagazine.dto.LoginDto;
import com.lina.springmagazine.dto.UserDto;
import com.lina.springmagazine.model.ResponseMsg;
import com.lina.springmagazine.security.UserDetailsServiceImpl;
import com.lina.springmagazine.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.HttpSession;

//@RestController
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsService;

    // 회원 로그인 페이지
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // 회원 로그인 페이지
    @GetMapping("/api/register")
    public String register() {
        return "signup";
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

    /**
     * 로그인
     * @param loginDto - 로그인에 필요한 정보를 담고있는 Dto
     * @param session - 인증된 유저일 때 session 정보를 저장
     * @return - tokenId 발급 -> header를 통해 전달됨
     */
    //@ApiOperation(value = "로그인", notes = "Spring Security를 통한 로그인 API")
    @PostMapping("/api/login")
    //public ResponseEntity<?> login(@RequestBody LoginDto loginDto, HttpSession session) {
    public ResponseEntity<ResponseMsg> login(@RequestBody LoginDto loginDto, HttpSession session) {
        ResponseMsg responseMsg;
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();

        LoginDto user = userDetailsService.findById(username);

        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(username, password);

        Authentication authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                SecurityContextHolder.getContext());

        user.setTokenId(RequestContextHolder.currentRequestAttributes().getSessionId());

        //return ResponseEntity.ok(user);
        responseMsg = new ResponseMsg("success", "로그인 성공");
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
