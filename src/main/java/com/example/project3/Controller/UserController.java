package com.example.project3.Controller;

import com.example.project3.DTO.UserDTO;
import com.example.project3.Entity.User;
import com.example.project3.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

    @GetMapping("Usertype")
    public String Usertype() {
        return "Usertype";
    }


    @PostMapping("/loginUser")
    public String login(@RequestParam String username, @RequestParam String password) {
        // Spring Security에서 로그인은 보통 필터가 처리함
        return "redirect:/"; // 성공 시 리디렉션 페이지
    }

    @GetMapping("/Join")
    public String join(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "join";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute UserDTO userDTO, BindingResult bindingResult) {
        System.out.println("여기임 ==================");
        System.out.println(userDTO);
        System.out.println("여기임 ==================");
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                System.out.println("필드: " + error.getField() + ", 에러 메시지: " + error.getDefaultMessage());
            }
            return "/join";
        }
        try {
            User registeredUser = userService.registerUser(userDTO);
            return "/MainPage";
        } catch (IllegalArgumentException e) {
            return "/Error404";
        }
    }
//    @PostMapping("/register")
//    public String registerUser(@Valid @ModelAttribute UserDTO userDTO, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            // 유효성 검사에 실패한 경우 처리
//            return "registrationForm";
//        }
//        // 유효성 검사가 성공한 경우 사용자 등록 처리
//        return "redirect:/success";
//    }
//@PostMapping("/register")
//public ResponseEntity<String> registerUser(@Valid @RequestBody UserDTO userDTO) {
//    userService.registerUser(userDTO);
//    return ResponseEntity.ok("User registered successfully");
//}
//
//    @GetMapping("/logout")
//    public String logout(HttpServletRequest request, HttpServletResponse response){
//        new SecurityContextLogoutHandler().logout(request, response,
//                SecurityContextHolder.getContext().getAuthentication());
//        return "redirect:/Login";
//    }
}
