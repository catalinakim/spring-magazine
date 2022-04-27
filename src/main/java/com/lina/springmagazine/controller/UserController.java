package com.lina.springmagazine.controller;

import com.lina.springmagazine.dto.LoginDto;
import com.lina.springmagazine.dto.UserDto;
import com.lina.springmagazine.model.AuthenticationToken;
import com.lina.springmagazine.model.ResponseMsg;
//import com.lina.springmagazine.security.UserDetailsServiceImpl;
import com.lina.springmagazine.security.UserDetailsServiceImpl;
import com.lina.springmagazine.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//@RestController
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

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
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto, HttpServletRequest req,
                                   HttpSession session, HttpServletResponse response) {
//    public ResponseEntity<ResponseMsg> login(@RequestBody LoginDto loginDto, HttpSession session) {
        ResponseMsg responseMsg;
        try {
            String username = loginDto.getUsername();
            String password = loginDto.getPassword();  //null

            //LoginDto user = userDetailsService.findById(username);

            // 아이디와 패스워드로, Security 가 알아 볼 수 있는 token 객체로 변경한다.
            UsernamePasswordAuthenticationToken token =
                    new UsernamePasswordAuthenticationToken(username, password);

            // AuthenticationManager 에 token 을 넘기면 UserDetailsService 가 받아 처리하도록 한다.
            // Authentication 는 인증정보를 가지는 인터페이스
            // authenticated() 메서드를 통해 인증되었는지 안되었는지 확인
            // AuthenticationManager 인증 과정을 수행, 인증에 성공하면 authentication 인스턴스 리턴
            Authentication authentication = authenticationManager.authenticate(token);

            // 실제 SecurityContext 에 authentication 정보를 등록한다.
            //인증된 사용자 정보인 `Principal`을 `Authentication`에서 관리하고
            //`Authentication`을 `SecurityContext`가 관리하고
            //`SecurityContext`는 `SecurityContextHolder`가 관리
            // 로그인 생성 후에 토큰을 스레드 내 인증정보의 저장소 역할을 하는 SecurityContextHolder 에 저장하고 사용하게 됩니다.
            // Spring 으로 요청이 들어왔을때 헤더의 인증정보를 스레드 내 저장소에 담아놓고 해당 스레드에서 필요 시 꺼내서 사용
            SecurityContextHolder.getContext().setAuthentication(authentication);

            //Session 에 저장되는 키는 “SPRING_SECURITY_CONTEXT”인데, 이 키는 HttpSessionSecurityContextRepository 클래스에 정의 되있다.
            session = req.getSession(true);
            session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    SecurityContextHolder.getContext());
//            session.setMaxInactiveInterval(60 * 60 * 24); // 24시간 유지, 디폴트는 30분

            // RequestContextHolder : Request 에 대한 정보를 가져오고자 할 때 사용
            // 두 메소드 모두 현재 스레드에 바인딩된 RequestAttributes 를 가져온다는 것은 동일하나
            // getRequestAttributes()는 RequestAttributes 가 없으면 널을 반환하고,
            // currentRequestAttributes()는  RequestAttributes 가 없으면 예외를 발생
            //user.setTokenId(RequestContextHolder.currentRequestAttributes().getSessionId());

            //쿠키에 시간 정보를 주지 않으면 세션 쿠키가 된다. (브라우저 종료시 모두 종료)
            //Cookie cookie = new Cookie("JSESSIONID", RequestContextHolder.currentRequestAttributes().getSessionId());
            //response.addCookie(cookie);

            //return ResponseEntity.ok(user);
            responseMsg = new ResponseMsg("success", "로그인 성공");
            //return new ResponseEntity(responseMsg, HttpStatus.OK);
            //return ResponseEntity.ok().body(responseMsg);
            //return ResponseEntity.ok().build();

            //https://cusonar.tistory.com/17?category=607756
            //return new AuthenticationToken(authentication.getName(), session.getId());
            return new ResponseEntity(new AuthenticationToken(authentication.getName(), session.getId()), HttpStatus.OK);


//            if (authentication.isAuthenticated() == true) {
//                responseMsg = new ResponseMsg("success", "로그인 성공");
//                return new ResponseEntity(responseMsg, HttpStatus.OK);
//            } else {
//                responseMsg = new ResponseMsg("fail", "로그인 실패");
//                return new ResponseEntity(responseMsg, HttpStatus.BAD_REQUEST);
//            }

        }catch (UsernameNotFoundException e){
            responseMsg = new ResponseMsg("fail", "로그인 실패, UsernameNotFoundException " + e.getMessage());
            return new ResponseEntity(responseMsg, HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            responseMsg = new ResponseMsg("fail", "로그인 실패," + e.getMessage());
            return new ResponseEntity(responseMsg, HttpStatus.BAD_REQUEST);
        }
    }

}
