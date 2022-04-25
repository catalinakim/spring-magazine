package com.lina.springmagazine.security.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

//@Component
public class LoginFailureHandler implements AuthenticationFailureHandler {
    //private final String DEFAULT_FAILURE_URL = "/login?error=true";
    private final String DEFAULT_FAILURE_URL = "/error";
    //JSON 컨텐츠를 Java 객체로 deserialization 하거나 Java 객체를 JSON으로 serialization
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        System.out.println("in LoginFailureHandler");
        //response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");

        //https://u2ful.tistory.com/35
//        String errorMessage = null;
//
//        //< incorrect the identify or password
//        //BadCredentialsException 은 아이디가 존재하지 않거나, 비밀번호가 틀린 경우 발생하는 exception
//        //InternalAuthenticationServiceException 존재하지 않는 아이디일 때 던지는 예외
//        if(exception instanceof BadCredentialsException || exception instanceof InternalAuthenticationServiceException) {
//            System.out.println("check id or pw");
//            errorMessage = "아이디나 비밀번호가 맞지 않습니다. 다시 확인해 주십시오.";
//            //response.setStatus(400);
//        }
//        //< account is disabled
//        else if(exception instanceof DisabledException) {
//            System.out.println("비활성화");
//            errorMessage = "계정이 비활성화 되었습니다. 관리자에게 문의하세요.";
//        }
//        //< expired the credential
//        else if(exception instanceof CredentialsExpiredException) {
//            System.out.println("유효기간 만료");
//            errorMessage = "비밀번호 유효기간이 만료 되었습니다. 관리자에게 문의하세요.";
//        }
//        else {
//            System.out.println("다른 이유");
//            errorMessage = "알수 없는 이유로 로그인에 실패하였습니다. 관리자에게 문의하세요.";
//        }

        //< set attributes
        //request.setAttribute()메서드로 저장된 키이름 값을 유지하려면 포워드로 이동해야한다.
        //request.setAttribute("errorMessage", errorMessage);

        //< redirection
        // RequestDispatcher객체는 다른 페이지로 이동하는 forward() 또는 include() 메소드를 가지고 있는 객체입니다.
        // .forward(request,response) 두 객체는 해당주소에 대해 요청하고 응답하라 라는 뜻이 담겨있다.
        //request.getRequestDispatcher(DEFAULT_FAILURE_URL).forward(request, response);

        //https://www.baeldung.com/spring-security-custom-authentication-failure-handler
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Map<String, Object> data = new HashMap<>();
        data.put(
                "timestamp",
                Calendar.getInstance().getTime());
        data.put(
                "exception",
                exception.getMessage());
        //response.setContentType("text/plain; charset=UTF-8");
        //response.setCharacterEncoding("UTF-8");
        response.getOutputStream()
                //.println(objectMapper.writeValueAsString(data));  //String타입으로 변환할 대상
                .println("login fail~~~~~~~~~~~");

    }
}

//출처: https://netframework.tistory.com/entry/REST-API-구성시-Spring-Security-구현 [Programming is Fun]