package com.example.project3.Config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Log4j2
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http    .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/", "/index", "/CreateAccount/**","/register","/StoreList/**","/css/**", "/js/**", "/img/**" ,"/myCart","/StoreDetails","/addToCart","deleteCartItem").permitAll()  // 메인 페이지, 회원가입, 정적 리소스는 누구나 접근 가능
                                .anyRequest().authenticated()  // 그 외의 요청은 인증된 사용자만 접근 가능
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")  // 커스텀 로그인 페이지 설정
                                .loginProcessingUrl("/loginTry")  // 로그인 처리 URL
                                .defaultSuccessUrl("/", true)  // 로그인 성공 시 메인 페이지로 리다이렉트
                                .permitAll()
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login?logout")  // 로그아웃 성공 시 로그인 페이지로 리다이렉트
                                .permitAll()
                );

        return http.build();
    }

    // 인증 관리자 관련 설정
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // 패스워드 인코더로 사용할 빈 등록
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
