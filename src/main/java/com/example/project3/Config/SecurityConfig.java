package com.example.project3.Config;

import jakarta.servlet.http.HttpSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers("/", "/userinfo", "/index", "/CreateAccount/**", "/register/**", "/StoreList/**", "/css/**", "/js/**", "/img/**", "/myCart", "/StoreDetails", "/addToCart", "/deleteCartItem", "/isExist/**","/edit/**").permitAll()  // 메인 페이지, 회원가입, 정적 리소스는 누구나 접근 가능
                                .anyRequest().authenticated()  // 그 외의 요청은 인증된 사용자만 접근 가능
                )
                .formLogin(formLogin ->
                        formLogin
                                .loginPage("/login")  // 커스텀 로그인 페이지 설정
                                .loginProcessingUrl("/loginTry")  // 로그인 처리 URL
                                .defaultSuccessUrl("/LoginSuccess", true)  // 로그인 성공 시 메인 페이지로 리다이렉트
                                .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .addLogoutHandler((request, response, authentication) -> {
                            HttpSession session = request.getSession();
                            session.invalidate();
                        })
                        .logoutSuccessHandler((request, response, authentication) ->
                                response.sendRedirect("/"))
                );

        return http.build();
    }
}