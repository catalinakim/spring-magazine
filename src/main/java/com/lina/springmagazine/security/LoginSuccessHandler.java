package com.lina.springmagazine.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//public class LoginSuccessHandler implements AuthenticationSuccessHandler {
//
//    private String username;
//    private String defaultUrl;
//
//    @Override
//    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//                                        Authentication authentication) throws IOException, ServletException {
//        //https://galid1.tistory.com/698
//        HttpSession session = request.getSession();
//        session.setAttribute("greeting", authentication.getName() + "님 반갑습니다.");
//        response.sendRedirect("/");
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getDefaultUrl() {
//        return defaultUrl;
//    }
//
//    public void setDefaultUrl(String defaultUrl) {
//        this.defaultUrl = defaultUrl;
//    }
//
//}
//출처: https://to-dy.tistory.com/94 [todyDev]