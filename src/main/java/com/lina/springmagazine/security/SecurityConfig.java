package com.lina.springmagazine.security;

//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.builders.WebSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)  //어노테이션 기반의 보안을 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/js/**");
        web.ignoring().antMatchers("/h2-console/**");
        web.ignoring().antMatchers("/api/posts");  //for ARC?
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception{
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

                 //그 외 어떤 요청이든 '인증'받아
                .anyRequest().authenticated()
                .and()
                    // [로그인 기능]
                    .formLogin()
                    .loginPage("/login")  // 접근이 차단된 페이지 클릭시 이동할 url
                    .usernameParameter("nickname")      // form 태그 내 id에 맵핑되는 name
                    .loginProcessingUrl("/api/login") // 로그인 처리시 맵핑되는 url
                    .defaultSuccessUrl("/", true)  // 로그인 처리 후 성공 시 URL
                    .failureUrl("/error")  // 로그인 처리 후 실패 시 URL
                    .permitAll() //무조건 접근을 허용 */
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .deleteCookies("JSESSIONID")
                    .permitAll();
                    //.logoutSuccessHandler(logoutSuccessHandler());

    }

}
