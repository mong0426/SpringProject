package com.example.project3.Controller;

import com.example.project3.DTO.StoreDetailsDTO;
import com.example.project3.Service.StoreDetailsService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
@Log4j2
@RequiredArgsConstructor
public class MainController {

    private final StoreDetailsService service;

    @GetMapping
    public String mainPage() {
        return "MainPage";
    }

    @GetMapping("/StoreDetails")
    @Transactional
    public String storeDetails(long sno, Model model) {
        StoreDetailsDTO dto = service.showStore(sno);
        model.addAttribute("storeDetails", dto);
        return "StoreDetails";
    }

    @GetMapping("/StoreList")
    public String StoreList() {
        return "StoreList";
    }
}
