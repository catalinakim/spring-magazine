package com.lina.springmagazine.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

//CORS : https://www.youtube.com/watch?v=1bcBddLXns8
//@Configuration
//public class WebConfig implements WebMvcConfigurer {
//
//    //@Bean //http://honeymon.io/tech/2018/03/13/spring-boot-mvc-controller.html
//    @Override
//    public void addCorsMappings(CorsRegistry registry){
//        registry.addMapping("/**")  //CORS를 적용할 URL 패턴을 정의
//                .allowedOrigins("*") //자원 공유를 허락할 Origin
//                .allowedMethods("*");  //"GET","POST","PUT","DELETE"
//    }
//}
