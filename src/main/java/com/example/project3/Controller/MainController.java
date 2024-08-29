package com.example.project3.Controller;

import com.example.project3.DTO.*;
import com.example.project3.Entity.Stores;
import com.example.project3.Entity.UserLikeStore;
import com.example.project3.Service.*;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
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
    private final OrderHistoryService orderHistoryService;
    private final FoodsService foodsService;
    private final StoreImageService storeImageService;
    private final UserService userService;

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
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        boolean likeStore = userService.IsLikeStore(id, sno);
        model.addAttribute("likeStore", likeStore);
        model.addAttribute("storeDetails", dto);
        model.addAttribute("averageRating", averageRating);
        return "StoreDetails";
    }

    @GetMapping("/myCart")
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
            @SuppressWarnings("unchecked") List<CartItemDTO> cartItems = (List<CartItemDTO>) session.getAttribute("cartItems");
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

    @PostMapping("/isExist/store")
    @ResponseBody
    public boolean isExistStore(@RequestBody StoreDetailsDTO storeDetailsDTO) {
        String store = storeDetailsDTO.getStore();
        System.out.println("store =========== " + store);
        return service.isExistStore(store);
    }

    @PostMapping("/deleteCartItem")
    @ResponseBody
    public Map<String, Object> deleteCartItem(@RequestBody String index, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            String numericValue = index.replaceAll("[^0-9]", "");
            int itemIndex = Integer.parseInt(numericValue);
            @SuppressWarnings("unchecked") List<CartItemDTO> cartItems = (List<CartItemDTO>) session.getAttribute("cartItems");
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

    @PostMapping("/add-to-order-history")
    @ResponseBody
    public Map<String, Object> addToOrderHistory(@RequestBody OrderHistoryDTO orderHistoryDTO, HttpSession httpSession) {
        Map<String, Object> response = new HashMap<>();
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        orderHistoryDTO.setId(id);
        orderHistoryDTO.setOrderDate(LocalDateTime.now());
        service.increaseOrderCount(orderHistoryDTO.getStore());
        try {
            orderHistoryService.saveOrderHistory(orderHistoryDTO);
            response.put("message", "주문 목록 저장 성공");
            httpSession.removeAttribute("cartItems");
        } catch (Exception e) {
            response.put("message", "주문 목록 저장 실패");
            response.put("error", e.getMessage());
        }
        return response;
    }

    @PostMapping("/edit/store")
    public String changeStoreInfo(@ModelAttribute StoreDetailsDTO storeDetailsDTO) {
        try {
            service.changeStoreInfo(storeDetailsDTO);

            return "redirect:/StoreDetails?sno=" + storeDetailsDTO.getSno();
        } catch (Exception e) {
            // 실패 시 오류 페이지로 리다이렉트
            return "/Error404";
        }
    }

    @PostMapping("/edit/foods")
    public String addNewFood(@ModelAttribute FoodsDTO foodsDTO, @RequestParam("multipartFile") MultipartFile multipartFile, @RequestParam("sno") Long sno) {
        try {
            String uploadDir = "D:\\Oseong\\SpringBoots\\SpringProject\\src\\main\\resources\\static\\img\\foodimg\\";
            String originalFilename = multipartFile.getOriginalFilename();
            String filePath = uploadDir + originalFilename;

            File directory = new File(uploadDir);

            if (!directory.exists()) {
                directory.mkdirs();
            }

            File uploadFile = new File(filePath);
            multipartFile.transferTo(uploadFile);

            String imageUrl = "img/foodimg/" + originalFilename;
            foodsDTO.setImageUrl(imageUrl);
            Stores stores = service.findBySno(sno);
            foodsDTO.setStore(stores);
//            System.out.println("FOODName" + foodsDTO.getFood());
//            System.out.println("Description" + foodsDTO.getDescription());
//            System.out.println("Price" + foodsDTO.getPrice());
//            System.out.println("ImageUrl" + foodsDTO.getImageUrl());
//            System.out.println("Store" + foodsDTO.getStore());

            foodsService.addNewFood(foodsDTO);

            return "redirect:/StoreDetails?sno=" + sno;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "/Error404";
        }
    }

    @PostMapping("/edit/store-img")
    public String addNewFood(@ModelAttribute StoreImagesDTO storeImagesDTO, @RequestParam("file") MultipartFile multipartFile, @RequestParam("sno") Long sno) {
        try {
            String uploadDir = "D:\\Oseong\\SpringBoots\\SpringProject\\src\\main\\resources\\static\\img\\storedetailimg\\";
            String originalFilename = multipartFile.getOriginalFilename();
            String filePath = uploadDir + originalFilename;

            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File uploadFile = new File(filePath);
            multipartFile.transferTo(uploadFile);

            String imageUrl = "img/storedetailimg/" + originalFilename;
            storeImagesDTO.setImageUrl(imageUrl);
            Stores stores = service.findBySno(sno);
            storeImagesDTO.setStores(stores);

            storeImageService.uploadStoreImage(storeImagesDTO);

            return "redirect:/StoreDetails?sno=" + sno;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "/Error404";
        }
    }

    @PostMapping("/change-like")
    @ResponseBody
    public Map<String, Object> changeLikeStatus(@RequestBody UserLikeStoreDTO userLikeStoreDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            String id = SecurityContextHolder.getContext().getAuthentication().getName();
            userLikeStoreDTO.setId(id);
            userService.changeLikes(id, userLikeStoreDTO.getSno());
            response.put("success", "good");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.put("error", "error");
        }
        return response;
    }
}