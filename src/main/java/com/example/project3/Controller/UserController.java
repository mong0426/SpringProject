package com.example.project3.Controller;

import com.example.project3.DTO.UserDTO;
import com.example.project3.Service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

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

    @PostMapping("/loginUser")
    public String login(@RequestParam String username, @RequestParam String password) {
        // Spring Security에서 로그인은 보통 필터가 처리함
        return "redirect:/"; // 성공 시 리디렉션 페이지
    }

    @GetMapping("/Join")
    public String join() {
        return "Join";
    }

//    @PostMapping("/register")
//    public ResponseEntity<String> registerUser(@Valid @RequestBody UserDTO userDTO, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return ResponseEntity.badRequest().body("Invalid user data.");
//        }
//        try {
//            User registeredUser = userService.registerUser(userDTO);
//            return ResponseEntity.ok("User registered successfully: " + registeredUser.getUsername());
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute UserDTO userDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // 유효성 검사에 실패한 경우 처리
            return "registrationForm";
        }
        // 유효성 검사가 성공한 경우 사용자 등록 처리
        return "redirect:/success";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        new SecurityContextLogoutHandler().logout(request, response,
                SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/Login";
    }
}
