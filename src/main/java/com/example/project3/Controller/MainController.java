package com.example.project3.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    @GetMapping
    public String mainPage(){
        return "MainPage";
    }

    @GetMapping("/StoreDetails")
    public String storeDetails(){
        return "StoreDetails";
    }

    @GetMapping("/StoreList")
    public String StoreList(){
        return "StoreList";
    }
}
