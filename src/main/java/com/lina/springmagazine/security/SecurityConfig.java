package com.lina.springmagazine.security;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)  //어노테이션 기반의 보안을 활성화
//@RequiredArgsConstructor
//@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsServiceImpl userDetailsService;


    public SecurityConfig(UserDetailsServiceImpl userDetailsService) {
        super(); //부모 클래스의 멤버를 초기화할 수 있도록 해줍니다.
        this.userDetailsService = userDetailsService;
    }

//    @Bean
//    public BCryptPasswordEncoder encodePassword() {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public PasswordEncoder passwordEncoder() {
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

                 //그 외 어떤 요청이든 '인증'받아
                .anyRequest().authenticated()
                .and()
                // [로그인 기능]
                    .formLogin()
                    .loginPage("/login")  // 접근이 차단된 페이지 클릭시 이동할 url
                    //.usernameParameter("nickname")      // form 태그 내 id에 맵핑되는 name
                    //.loginProcessingUrl("/api/login") // 로그인 처리시 맵핑되는 url
                    //.defaultSuccessUrl("/")  // 로그인 처리 후 성공 시 URL
                    //.failureUrl("/error")  // 로그인 처리 후 실패 시 URL
                    //.successHandler(new LoginSuccessHandler())
                    .permitAll() //무조건 접근을 허용 */
                .and()
                    .logout()
                    .logoutUrl("/api/logout")
                    .deleteCookies("JSESSIONID")
                    .permitAll();
                    //.logoutSuccessHandler(logoutSuccessHandler());

    }

    // CORS 정책 필터
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        configuration.addExposedHeader("Authorization");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    /**
     * AuthenticationManager 생성
     * Security Login에서 사용 (Swagger)
     * 인증을 위해서 UserDetailsService를 통해 필요 정보를 가져온다.
     * adminService에서 UserDetailsService를 implements하여 loaduserbyUsername() 메서드 구현
     * 비밀번호 암호화
     */
    //@Autowired
    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * AuthenticationManager Bean 등록
     * Custom Login에서 사용
     * UsernamePasswordAuthenticationToken을 파라미터로 인증을 수행
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }



}
