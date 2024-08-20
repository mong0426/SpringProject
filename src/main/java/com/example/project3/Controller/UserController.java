package com.example.project3.Controller;

import com.example.project3.DTO.UserDTO;
import com.example.project3.Entity.CustomUserDetails;
import com.example.project3.Entity.User;
import com.example.project3.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.security.Principal;

@Controller
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/myPage")
    public String myPage() {
        return "myPage";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/LoginSuccess")
    public String loginSuccess() {
        return "LoginSuccess";
    }

    @GetMapping("CreateAccount")
    public String CreateAccount() {
        return "CreateAccount";
    }

    @GetMapping("/CreateAccount/User")
    public String CreateAccountUser(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "User";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute UserDTO userDTO, BindingResult bindingResult) {
//        System.out.println("여기임 ==================");
//        System.out.println(userDTO);
//        System.out.println("여기임 ==================");
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                System.out.println("필드: " + error.getField() + ", 에러 메시지: " + error.getDefaultMessage());
            }
            return "User";
        }
        try {
            User registeredUser = userService.registerUser(userDTO);
            return "WelcomePage";
        } catch (IllegalArgumentException e) {
            return "/Error404";
        }
    }

    @PostMapping("/isExist/id")
    @ResponseBody
    public boolean isExistId(@RequestBody UserDTO userDTO) {
        String id = userDTO.getUserid();
        System.out.println("id =========== " + id);
        return userService.isExistId(id);
    }
    @GetMapping("/userinfo")
    public String userInfo(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Object principal = auth.getPrincipal();

        if (principal instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) principal;
            System.out.println("Username ================= userDetails.getUsername " + userDetails.getUsername());
            model.addAttribute("username", userDetails.getUsername());
        } else if (principal instanceof String) {
            // principal이 String 타입인 경우
            System.out.println("Username ================= String" + principal);
            model.addAttribute("username", principal);
        } else {
            // 인증되지 않은 사용자 처리
            System.out.println("인증되지 않은 사용자입니다.");
        }
        return "MainPage";
    }
}
