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
@EnableGlobalMethodSecurity(securedEnabled = true)  //??????????????? ????????? ????????? ?????????
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

        // CorsConfigurationSource ??? cors ????????? ???????????? ??????
        http.cors()
                .configurationSource(corsConfigurationSource());

        // CSRF protection ??? ????????????
        http.csrf().disable();

        http.authorizeRequests()

                // image ????????? login ?????? ??????
                .antMatchers("/images/**").permitAll()
                // css ????????? login ?????? ??????
                .antMatchers("/css/**").permitAll()
                // ?????? ?????? ?????? API ????????? login ?????? ??????
                .antMatchers("/api/register").permitAll()
                .antMatchers("/api/login").permitAll()
                .antMatchers("/index").permitAll()
                .antMatchers("/api/**").permitAll()
                .antMatchers("/h2-console/**").permitAll()
                //https://oddpoet.net/blog/2017/04/27/cors-with-spring-security/
                .requestMatchers(CorsUtils::isPreFlightRequest).permitAll() // - (1)

                //??? ??? ?????? ???????????? '??????'??????
                .anyRequest().authenticated()
                .and()
                // [????????? ??????]
                    .formLogin()
                    .loginPage("/login")  // ????????? ????????? ????????? ????????? ????????? url
                    //.usernameParameter("nickname")      // form ?????? ??? id??? ???????????? name
                    .loginProcessingUrl("/api/login") // ????????? ????????? ???????????? url
//                    .defaultSuccessUrl("/")  // ????????? ?????? ??? ?????? ??? URL
//                    .failureUrl("/error")  // ????????? ?????? ??? ?????? ??? URL
                    .successHandler(loginSuccessHandler()) //test
                    .failureHandler(loginFailureHandler())  //test
                    .permitAll() //????????? ????????? ?????? */
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

    // CORS ?????? ??????
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");
        //ExpoesdHeader??? ?????????????????? ????????? ????????? ??? ?????? header?????? ????????? ??? ??????.
        //configuration.addExposedHeader("Authorization");
        configuration.setAllowCredentials(true); // ??????????????? ?????? ?????? ?????? (Authorization??? ????????? ?????? ?????? ??? true)
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
