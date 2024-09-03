package com.example.project3.Controller;

import com.example.project3.DTO.*;
import com.example.project3.Entity.Stores;
import com.example.project3.Service.*;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
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
    private static String UPLOAD_DIR = System.getProperty("user.dir") + "/src/main/resources/static/img/";

    @GetMapping
    public String mainPage(Model model) {
        String category1 = "치킨";
        List<Stores> categoryStore1 = service.findAllByStoreAndFood(category1);
        String category2 = "밥";
        List<Stores> categoryStore2 = service.findAllByStoreAndFood(category2);

        model.addAttribute("category1", categoryStore1);
        model.addAttribute("category2", categoryStore2);
        return "MainPage";
    }

    @GetMapping("/StoreDetails")
    @Transactional
    public String getStoreDetails(
            @RequestParam(name = "sno") long sno,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size,
            Model model) {
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
        Stores store = service.findBySno(sno);
        PageableReviewsDTO pageableReviews = service.getReviews(store, page, size);

        int currentPage = pageableReviews != null ? pageableReviews.getNumber() : 0;

        for (int i = 0; i < pageableReviews.getReviews().size(); i++) {
            int value = (int) pageableReviews.getReviews().get(i).getRating();
            pageableReviews.getReviews().get(i).setRating(value);
        }
        model.addAttribute("likeStore", likeStore);
        model.addAttribute("storeDetails", dto);
        model.addAttribute("averageRating", averageRating);
        model.addAttribute("reviews", pageableReviews.getReviews());
        model.addAttribute("totalPages", pageableReviews.getTotalPages());
        model.addAttribute("currentPage", pageableReviews.getNumber());
        return "StoreDetails";
    }

    @PostMapping("/review/page")
    @ResponseBody
    public Map<String, Object> getReviewPage(@RequestBody ReviewPageRequestDTO pageRequestDTO) {
        Map<String, Object> response = new HashMap<>();
        try {
            int page = 0;
            if (pageRequestDTO != null && pageRequestDTO.getPage() != null) {
                page = pageRequestDTO.getPage();
            }
            Stores store = service.findStoresByStore(pageRequestDTO.getStore());
            int size = 10;
            System.out.println("store == " + store + " page = " + page + " size = " + size);
            PageableReviewsDTO pageableReviews = service.getReviews(store, page, size);

            System.out.println("pageableReviews ====================" + pageableReviews);
            response.put("reviews", pageableReviews.getReviews());
            response.put("currentPage", page);
            response.put("totalPages", pageableReviews.getTotalPages());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return response;
    }

    @PostMapping("/review/write")
    public String writeReview(@ModelAttribute ReviewsDTO dto, OrderHistoryDTO orderHistoryDTO, Model model) {
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        dto.setWriter(id);
        Long ono = orderHistoryDTO.getOno();
        try {
            service.registerReview(dto);
            orderHistoryService.changeReviewed(ono);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Error404";
        }
        return "redirect:/myPage";
    }


    @GetMapping("/myCart")
    public String myCart() {
        return "myCart";
    }

    @GetMapping("/StoreList")
    public String StoreList(@RequestParam(value = "searchText", required = false) String searchText,
                            @RequestParam(value = "sort", required = false) String sort,
                            @RequestParam(value = "sortDirection", required = false) String sortDirection,
                            @RequestParam(value = "deliveryTip", required = false) String deliveryTip,
                            @RequestParam(value = "orderCount", required = false) Integer orderCount,
                            @RequestParam(value = "minOrder", required = false) String minOrder,
                            @RequestParam(name = "page", defaultValue = "0") Integer page,
                            @RequestParam(name = "size", defaultValue = "10") Integer size,
                            Model model) {
        int minOrderInteger = 99999999;
        if (minOrder != null) {
            if (!minOrder.equals("all")) {
                minOrderInteger = Integer.parseInt(minOrder);
            }
        }
        int deliTipInteger = 99999999;
        if (deliveryTip != null) {
            if (!deliveryTip.equals("all")) {
                deliTipInteger = Integer.parseInt(deliveryTip);
            }
        }

        if (sort != null) {
            switch (sort) {
                case "deliveryTipLow":
                    sort = "deliTip";
                    break;
                case "default":
                    sort = "sno";
                    break;
                case "favoritesHigh":
                    sort = "likes";
                    break;
            }
        }
        Page<Stores> stores = service.searchStore(searchText, sort, sortDirection, deliTipInteger, minOrderInteger, orderCount, page, size);
        List<Double> averageRatings = service.getAverageRatings(stores);

        model.addAttribute("stores", stores);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", stores.getTotalPages());
        model.addAttribute("averageRating", averageRatings);

        return "StoreList";
    }

    @PostMapping("/addToCart")
    @ResponseBody
    public Map<String, Object> addToCart(@RequestBody CartItemDTO cartItem, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            @SuppressWarnings("unchecked") List<CartItemDTO> cartItems = (List<CartItemDTO>) session.getAttribute("cartItems");
            System.out.println("cartItems ==============" + cartItems);
            if (cartItems == null) {
                cartItems = new ArrayList<>();
            } else {
                String currentStore = "";
                currentStore = cartItems.get(0).getStoreName();
                if (!currentStore.equals(cartItem.getStoreName()) && !cartItem.getStoreName().isEmpty()) {
                    System.out.println("다른 매장");
                    response.put("otherStore", "다른 매장 상품");
                    return response;
                }

            }
            cartItems.add(cartItem);
            session.setAttribute("cartItems", cartItems);
            response.put("success", "장바구니에 추가되었습니다.");
            response.put("cartItemsSize", cartItems.size());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.put("error", "장바구니에 아이템을 추가하는 중 오류가 발생했습니다.");
        }
        return response;
    }

    @PostMapping("/addToCart/otherStore")
    @ResponseBody
    public Map<String, Object> refreshCart(@RequestBody CartItemDTO cartItem, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        try {
            session.removeAttribute("cartItems");
            List<CartItemDTO> cartItems = new ArrayList<>();
            cartItems.add(cartItem);
            session.setAttribute("cartItems", cartItems);
            response.put("success", "장바구니에 추가되었습니다.");
            response.put("cartItemsSize", cartItems.size());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.put("error", "장바구니에 아이템을 추가하는 중 오류가 발생했습니다.");
        }
        return response;
    }

    @PostMapping("/isExist/store")
    @ResponseBody
    public boolean isExistStore(@RequestBody StoreDetailsDTO storeDetailsDTO) {
        String store = storeDetailsDTO.getStore();
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
            if (cartItems != null && itemIndex >= 0 && itemIndex <= cartItems.size()) {
                // 장바구니에서 해당 아이템 삭제
                cartItems.remove(itemIndex);
                // 세션에 업데이트된 장바구니 다시 저장
                session.setAttribute("cartItems", cartItems);

                if (cartItems.isEmpty()) {
                    session.removeAttribute("cartItems");
                }
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
    public Map<String, Object> addToOrderHistory(@RequestBody OrderHistoryDTO orderHistoryDTO, HttpSession
            httpSession) {
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
    public String addNewFood(@ModelAttribute FoodsDTO foodsDTO, @RequestParam(name = "multipartFile") MultipartFile
            multipartFile, @RequestParam(name = "sno") Long sno) {
        try {
            String uploadDir = UPLOAD_DIR + "foodimg/";
            String originalFilename = multipartFile.getOriginalFilename();
            String filePath = uploadDir + originalFilename;
            System.out.println("PATH ==============" + uploadDir);
            File directory = new File(uploadDir);

            if (!directory.exists()) {
                directory.mkdirs();
            }

            File uploadFile = new File(filePath);
            multipartFile.transferTo(uploadFile);

            String imageUrl = "/img/foodimg/" + originalFilename;
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
    public String addNewFood(@ModelAttribute StoreImagesDTO storeImagesDTO, @RequestParam(name = "file") MultipartFile
            multipartFile, @RequestParam(name = "sno") Long sno) {
        try {
            String uploadDir = UPLOAD_DIR + "storedetailimg/";
            String originalFilename = multipartFile.getOriginalFilename();
            String filePath = uploadDir + originalFilename;

            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File uploadFile = new File(filePath);
            multipartFile.transferTo(uploadFile);

            String imageUrl = "/img/storedetailimg/" + originalFilename;
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
            if (userLikeStoreDTO.getLikes().equals("false")) {
                service.increaseLikesBySno(userLikeStoreDTO.getSno(), -1);

            } else {
                service.increaseLikesBySno(userLikeStoreDTO.getSno(), 1);
            }
            Stores store = service.findBySno(userLikeStoreDTO.getSno());
            int updateLikes = store.getLikes();
            response.put("likes", updateLikes);
            response.put("success", "good");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            response.put("error", "error");
        }
        return response;
    }
}