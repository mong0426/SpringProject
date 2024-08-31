package com.example.project3.Controller;

import com.example.project3.DTO.OrderHistoryDTO;
import com.example.project3.DTO.StoreDetailsDTO;
import com.example.project3.DTO.UserDTO;
import com.example.project3.Entity.Seller;
import com.example.project3.Entity.Stores;
import com.example.project3.Entity.User;
import com.example.project3.Entity.UserLikeStore;
import com.example.project3.Service.OrderHistoryService;
import com.example.project3.Service.SellerService;
import com.example.project3.Service.StoreDetailsService;
import com.example.project3.Service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SellerService sellerService;
    private final OrderHistoryService orderHistoryService;
    private final StoreDetailsService storeDetailsService;

    @GetMapping("/myPage")
    public String myPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userid = authentication.getName();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        List<OrderHistoryDTO> orderHistories = orderHistoryService.showOrderList(userid);
        orderHistories.forEach(dto -> dto.setFormattedOrderDate(dto.getOrderDate().format(formatter)));

        List<UserLikeStore> userLikeStores = userService.findUserLikeStores(userid);
        List<StoreDetailsDTO> storeDetailsDTOS = new ArrayList<>();
        for (UserLikeStore userLikeStore : userLikeStores) {
            Stores stores = storeDetailsService.findBySno(userLikeStore.getSno());
            StoreDetailsDTO dto = storeDetailsService.entityToDto(stores);
            storeDetailsDTOS.add(dto);
        }

        model.addAttribute("orderHistories", orderHistories);
        model.addAttribute("userLikeStores", storeDetailsDTOS);

        return "myPage";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/LoginSuccess")
    public String loginSuccess(HttpSession session, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userid = authentication.getName();
        if (userService.isCustomerUser(userid)) {
            User user = userService.getUserInfo(userid);
            session.setAttribute("userName", user.getName());
            session.setAttribute("userEmail", user.getEmail());
            session.setAttribute("userPhone", user.getPhone());
            session.setAttribute("userAddress", user.getAddress());
            session.setAttribute("userBirth", user.getBirth());
            session.setAttribute("userCreateDate", user.getCreateDate());
            session.setAttribute("authentication", "user");
        } else {
            Seller seller = sellerService.getSellerInfo(userid);
            session.setAttribute("userName", seller.getStores().getCeo());
            session.setAttribute("userEmail", seller.getEmail());
            session.setAttribute("userPhone", seller.getStores().getTel());
            session.setAttribute("userAddress", seller.getStores().getAddr());
            session.setAttribute("userCreateDate", seller.getCreateDate());
            session.setAttribute("authentication", seller.getStores().getSno());
        }
        return "LoginSuccess";
    }

    @GetMapping("CreateAccount")
    public String CreateAccount() {
        return "CreateAccount";
    }

    @GetMapping("/CreateAccount/User")
    public String CreateAccountUser(Model model) {
        model.addAttribute("userDTO", new UserDTO());
        return "User";
    }

    @PostMapping("/register")
    public String registerUser(@Valid @ModelAttribute UserDTO userDTO, BindingResult bindingResult) {
//        System.out.println("여기임 ==================");
//        System.out.println(userDTO);
//        System.out.println("여기임 ==================");
        if (bindingResult.hasErrors()) {
            for (FieldError error : bindingResult.getFieldErrors()) {
                System.out.println("필드: " + error.getField() + ", 에러 메시지: " + error.getDefaultMessage());
            }
            return "User";
        }
        try {
            User registeredUser = userService.registerUser(userDTO);
            return "WelcomePage";
        } catch (IllegalArgumentException e) {
            return "/Error404";
        }
    }

    @PostMapping("/edit/user-info")
    @ResponseBody
    public Map<String, Object> editUserInfo(@ModelAttribute UserDTO userDTO, HttpSession httpSession) {
        Map<String, Object> response = new HashMap<>();
        try {
            String id = SecurityContextHolder.getContext().getAuthentication().getName();
            userDTO.setUserid(id);
            System.out.println("userDTO ============== " + userDTO);
            userService.editUser(userDTO);
            httpSession.setAttribute("userName", userDTO.getName());
            httpSession.setAttribute("userEmail", userDTO.getEmail());
            httpSession.setAttribute("userPhone", userDTO.getPhone());

            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
            response.put("error", e.getMessage());
        }
        return response;
    }

    @PostMapping("/isExist/id")
    @ResponseBody
    public boolean isExistId(@RequestBody UserDTO userDTO) {
        String id = userDTO.getUserid();
        System.out.println("id =========== " + id);
        return userService.isExistId(id);
    }

    @PostMapping("/changeAddress")
    @ResponseBody
    public Map<String, Object> changeAddress(@RequestBody UserDTO userDTO, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        String address = userDTO.getAddress();
        try {
            userService.updateAddress(id, address);
            session.setAttribute("userAddress", address);
            response.put("success", "주소 수정 성공");
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", "주소 수정 실패");
        }
        return response;
    }

    @PostMapping("/delete/account")
    @ResponseBody
    public Map<String, Object> deleteAccount(@RequestBody UserDTO userDTO, HttpSession session) {
        Map<String, Object> response = new HashMap<>();
        String id = SecurityContextHolder.getContext().getAuthentication().getName();
        String inputPass = userDTO.getPassword();
        if (userService.isPasswordMatch(inputPass, id)) {
            if (userService.isCustomerUser(id)) {
                userService.deleteUser(id);
            } else {
                sellerService.deleteSeller(id);
            }
            orderHistoryService.deleteOrderHistory(id);
            userService.deleteUserLikeStore(id);

            session.invalidate();
            response.put("success", true);
        } else {
            response.put("success", false);
        }

        return response;
    }
}
