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
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
        double totalRating = 0;
        for(int i = 0 ;i < dto.getReviews().size(); i++){
            totalRating+= dto.getReviews().get(i).getRating();
        }
        double averageRating = totalRating == 0 ? 0 : totalRating / dto.getReviews().size();
        model.addAttribute("storeDetails", dto);
        model.addAttribute("averageRating", averageRating);
        return "StoreDetails";
    }

    @GetMapping("/StoreList")
    public String StoreList(@RequestParam(name = "searchText", required = false, defaultValue = "") String searchText, Model model)
    {
        List <StoreDetailsDTO> stores = service.searchStore(searchText);
        model.addAttribute("stores",stores);

        return "StoreList";
    }

    @GetMapping("/myCart")
    public String myCart(){return "myCart";}

}
