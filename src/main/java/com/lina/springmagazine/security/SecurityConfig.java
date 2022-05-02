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
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.filter.CorsFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)  //어노테이션 기반의 보안을 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CorsFilter corsFilter;

    public SecurityConfig(CorsFilter corsFilter) {
        this.corsFilter = corsFilter;
    }


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
    protected void configure(HttpSecurity http) throws Exception{

        // CorsConfigurationSource 를 cors 정책의 설정으로 등록
        //http.cors().configurationSource(corsConfigurationSource());

        // CSRF protection 을 비활성화
        http.cors()
                .and()
                .csrf().disable();

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
                    //.usernameParameter("nickname")      // form 태그 내 id에 맵핑되는 name
                    .loginProcessingUrl("/api/login") // 로그인 처리시 맵핑되는 url
                    .defaultSuccessUrl("/")  // 로그인 처리 후 성공 시 URL
                    .failureUrl("/error")  // 로그인 처리 후 실패 시 URL
                    //.successHandler(new LoginSuccessHandler())  //프론트 CORS 해결위해 테스트
                    // 시큐리티 예외처리
                    // addFilterBefore : 지정된 필터 앞에 커스텀 필터를 추가 (UsernamePasswordAuthenticationFilter 보다 먼저 실행된다)
                    //.addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter.class) //필터가 없어서 안되는듯
                    .permitAll() //무조건 접근을 허용 */
                .and()
                    .logout()
                    .logoutUrl("/api/logout")
                    .deleteCookies("JSESSIONID")
                    .permitAll();
                    //.logoutSuccessHandler(logoutSuccessHandler());

    }

    // CORS 정책 필터
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration configuration = new CorsConfiguration();
//
//        configuration.setAllowCredentials(true); // 자격증명과 함께 요청 여부 (Authorization로 사용자 인증 사용 시 true)
//        //configuration.addAllowedOrigin("*");
//        configuration.addAllowedOrigin("http://localhost:3000");
//        configuration.addAllowedOrigin("http://localhost:7070");
//        configuration.addAllowedHeader("*");
//        configuration.addAllowedMethod("*");
//        configuration.addExposedHeader("Authorization");
//
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }



}
