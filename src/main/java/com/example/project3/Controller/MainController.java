package com.example.project3.Controller;

import com.example.project3.DTO.CartItemDTO;
import com.example.project3.DTO.StoreDetailsDTO;
import com.example.project3.Service.StoreDetailsService;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String storeDetails(@RequestParam("sno") long sno, Model model) {
        StoreDetailsDTO dto = service.showStore(sno);
        double totalRating = 0;
        if (dto.getReviews() != null) {
            for (int i = 0; i < dto.getReviews().size(); i++) {
                totalRating += dto.getReviews().get(i).getRating();
            }
        }
        DecimalFormat df = new DecimalFormat("#.0");
        double averageRating = totalRating == 0 ? 0 : totalRating / dto.getReviews().size();
        averageRating = Double.parseDouble(df.format(averageRating));
        model.addAttribute("storeDetails", dto);
        model.addAttribute("averageRating", averageRating);
        return "StoreDetails";
    }

    @GetMapping("myCart")
    public String myCart() {
        return "myCart";
    }

    @GetMapping("/StoreList")
    public String StoreList(@RequestParam(name = "searchText", required = false, defaultValue = "") String searchText, Model model) {
        List<StoreDetailsDTO> stores = service.searchStore(searchText);
        model.addAttribute("stores", stores);

        return "StoreList";
    }

    @PostMapping("/addToCart")
    @ResponseBody
    public Map<String, Object> addToCart(@RequestBody CartItemDTO cartItem, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            @SuppressWarnings("unchecked")
            List<CartItemDTO> cartItems = (List<CartItemDTO>) session.getAttribute("cartItems");
            if (cartItems == null) {
                cartItems = new ArrayList<>();
            }
            cartItems.add(cartItem);
            session.setAttribute("cartItems", cartItems);
            response.put("message", "장바구니에 추가되었습니다.");
            response.put("cartItemsSize", cartItems.size());
        } catch (Exception e) {
            response.put("error", "장바구니에 아이템을 추가하는 중 오류가 발생했습니다.");
        }
        return response;
    }

    @PostMapping("/deleteCartItem")
    @ResponseBody
    public Map<String, Object> deleteCartItem(@RequestBody String index, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            String numericValue = index.replaceAll("[^0-9]", "");
            int itemIndex = Integer.parseInt(numericValue);
            @SuppressWarnings("unchecked")
            List<CartItemDTO> cartItems = (List<CartItemDTO>) session.getAttribute("cartItems");
//            System.out.println("item index : "+itemIndex +" cartItems.size ====== "+cartItems.size());
            if (cartItems != null && itemIndex >= 0 && itemIndex <= cartItems.size()) {
                // 장바구니에서 해당 아이템 삭제
                cartItems.remove(itemIndex);
                // 세션에 업데이트된 장바구니 다시 저장
                session.setAttribute("cartItems", cartItems);
                response.put("cartItemsSize", cartItems.size());
                response.put("success", true);
                response.put("empty", cartItems.isEmpty());
                response.put("message", "아이템이 성공적으로 삭제되었습니다.");
            } else {
                response.put("success", "false");
                response.put("message", "잘못된 인덱스이거나 장바구니가 비어 있습니다.");
            }
        } catch (NumberFormatException e) {
            response.put("success", "false");
            response.put("message", "잘못된 인덱스 값입니다.");
        }
        return response;
    }
}