package com.lina.springmagazine.security.login;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@RequiredArgsConstructor
//@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    //현재 클라이언트의 요청 과정에서 생성되거나 참조되는 모든 정보는 DefaultSavedRequest 에 저장되고 이 객체는 HttpSessionRequestCache 에 의해 세션에 저장됩니다.
    private RequestCache requestCache = new HttpSessionRequestCache();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
        System.out.println("in LoginSuccessHandler onAuthenticationSuccess()");

        //https://jason-moon.tistory.com/141 [Programmer]
        //super.onAuthenticationSuccess(request, response, authentication);

    }

    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        SavedRequest savedRequest = requestCache.getRequest(request, response); //세션에 저장된 DefaultSavedRequest 를 가져옴
        if (savedRequest == null) {
            clearAuthenticationAttributes(request);
            System.out.println("savedRequest is null");
            return;
        }
        String targetUrlParam = getTargetUrlParameter();
        if (isAlwaysUseDefaultTargetUrl() || (targetUrlParam != null && StringUtils.hasText(request.getParameter(targetUrlParam)))) {
            requestCache.removeRequest(request, response);
            clearAuthenticationAttributes(request);
            System.out.println("if targetUrlParam is not null");
            return;
        }
        clearAuthenticationAttributes(request);
    }
}

//https://netframework.tistory.com/entry/REST-API-구성시-Spring-Security-구현


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


