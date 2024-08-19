package com.example.project3.Controller;

import com.example.project3.DTO.SellerDTO;
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

    @GetMapping("CreateAccount")
    public String CreateAccount() {
        return "CreateAccount";
    }

    @PostMapping("/loginUser")
    public String login(@RequestParam String username, @RequestParam String password) {
        // Spring Security에서 로그인은 보통 필터가 처리함
        return "redirect:/"; // 성공 시 리디렉션 페이지
    }

    @GetMapping("/CreateAccount/User")
    public String CreateAccountUser(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "User";
    }

    @GetMapping("CreateAccount/Seller")
    public String CreateAccountSeller(Model model) {
        model.addAttribute("sellerDTO", new SellerDTO());
        return "Seller";
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
            return "/CreateAccount/User";
        }
        try {
            User registeredUser = userService.registerUser(userDTO);
            return "WelcomePage";
        } catch (IllegalArgumentException e) {
            return "/Error404";
        }
    }

//
//    @GetMapping("/logout")
//    public String logout(HttpServletRequest request, HttpServletResponse response){
//        new SecurityContextLogoutHandler().logout(request, response,
//                SecurityContextHolder.getContext().getAuthentication());
//        return "redirect:/Login";
//    }
}
