package com.lina.springmagazine.security;

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.lina.springmagazine.security.login.LoginFailureHandler;
import com.lina.springmagazine.security.login.LoginSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
//@ComponentScan(basePackages = "com.lina.springmagazine.security")
@EnableGlobalMethodSecurity(securedEnabled = true)  //어노테이션 기반의 보안을 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //    @Autowired
    //private LoginSuccessHandler loginSuccessHandler;
    //private LoginFailureHandler loginFailureHandler;
//    private AjaxAwareAuthenticationEntryPoint ajaxAwareAuthenticationEntryPoint(String url) {
//        return new AjaxAwareAuthenticationEntryPoint(url);
//    }


    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**");
        web.ignoring().antMatchers("/h2-console/**");
        //web.ignoring().antMatchers("/api/posts");  //for ARC?
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // CorsConfigurationSource 를 cors 정책의 설정으로 등록
        http.cors()
                .configurationSource(corsConfigurationSource());

        // CSRF protection 을 비활성화
        http.csrf().disable();

        http.authorizeRequests()

                // image 폴더를 login 없이 허용
                .antMatchers("/images/**").permitAll()
                // css 폴더를 login 없이 허용
                .antMatchers("/css/**").permitAll()
                // 회원 관리 처리 API 전부를 login 없이 허용
                .antMatchers("/api/register").permitAll()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                //https://oddpoet.net/blog/2017/04/27/cors-with-spring-security/
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll() // - (1)

                //그 외 어떤 요청이든 '인증'받아
                .anyRequest().authenticated()
                .and()
                // [로그인 기능]
                    .formLogin()
                    .loginPage("/login")  // 접근이 차단된 페이지 클릭시 이동할 url
                    //.usernameParameter("nickname")      // form 태그 내 id에 맵핑되는 name
                    .loginProcessingUrl("/api/login") // 로그인 처리시 맵핑되는 url
//                    .defaultSuccessUrl("/")  // 로그인 처리 후 성공 시 URL
//                    .failureUrl("/error")  // 로그인 처리 후 실패 시 URL
                    .successHandler(loginSuccessHandler()) //test
                    .failureHandler(loginFailureHandler())  //test
                    .permitAll() //무조건 접근을 허용 */
                .and()
                    .logout()
                    .logoutUrl("/api/logout")
                    .deleteCookies("JSESSIONID")
                    .permitAll();
                    //.logoutSuccessHandler(logoutSuccessHandler());
                //.and().exceptionHandling()
                    //.authenticationEntryPoint(ajaxAwareAuthenticationEntryPoint("/api/login"))
                    //.accessDeniedPage("/error");
                    //https://graykang.tistory.com/58 [graykang]

    }

    // CORS 정책 필터
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        //ExpoesdHeader에 클라이언트가 응답에 접근할 수 있는 header들을 추가할 수 있다.
        //configuration.addExposedHeader("Authorization");
        configuration.setAllowCredentials(true); // 자격증명과 함께 요청 여부 (Authorization로 사용자 인증 사용 시 true)
        configuration.setMaxAge(3600L); // preflight request cache time
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        return super.authenticationManagerBean();
//    }

    @Bean
    public AuthenticationSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler();
    }

    @Bean
    public AuthenticationFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

}
