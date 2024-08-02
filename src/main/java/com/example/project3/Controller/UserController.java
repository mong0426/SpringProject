package com.example.project3.Controller;

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

    @GetMapping("/Join")
    public String join() {
        return "Join";
    }

}
