package com.example.project3.Controller;

import com.example.project3.DTO.SellerDTO;
import com.example.project3.DTO.StoreDetailsDTO;
import com.example.project3.Entity.Seller;
import com.example.project3.Service.SellerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SellerController {

    private final SellerService service;

    @GetMapping("CreateAccount/Seller")
    public String CreateAccountSeller(Model model) {
        model.addAttribute("sellerDTO", new SellerDTO());
        return "Seller";
    }

    @PostMapping("/register/Seller")
    public String registerUser(@Valid @ModelAttribute SellerDTO sellerDTO, BindingResult bindingResult) {
        System.out.println("여기임 ==================");
        System.out.println(sellerDTO);
        System.out.println("여기임 ==================");
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                System.out.println("필드: " + error.getField() + ", 에러 메시지: " + error.getDefaultMessage());
            }
            return "Seller";
        }
        try {
            service.createStoreAndSeller(sellerDTO);
            return "WelcomePage";
        } catch (IllegalArgumentException e) {
            return "/Error404";
        }
    }
}
