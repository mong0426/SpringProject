package com.example.project3.Controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    @GetMapping("/myPage")
    public String myPage() {
        return "myPage";
    }

    @GetMapping("/Login")
    public String login() {
        return "Login";
    }
//
//    @PostMapping("/login")
//    public String login(@Valid User user, BindingResult bindingResult) {
//        return "login";
//    }

    @GetMapping("/Join")
    public String join() {
        return "Join";
    }
//
//    @PostMapping("/Join")
//    public String join(@Valid User user, BindingResult bindingResult) {
//        return "join";
//    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        new SecurityContextLogoutHandler().logout(request, response,
                SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }
}

