package com.example.project3.Controller;

import com.example.project3.DTO.OrderHistoryDTO;
import com.example.project3.DTO.UserDTO;
import com.example.project3.Entity.Seller;
import com.example.project3.Entity.User;
import com.example.project3.Service.OrderHistoryService;
import com.example.project3.Service.SellerService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SellerService sellerService;
    private final OrderHistoryService orderHistoryService;

    @GetMapping("/myPage")
    public String myPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userid = authentication.getName();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<OrderHistoryDTO> orderHistories = orderHistoryService.showOrderList(userid);
        orderHistories.forEach(dto -> dto.setFormattedOrderDate(dto.getOrderDate().format(formatter)));
        model.addAttribute("orderHistories", orderHistories);
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
        if (userService.isCoustomerUser(userid)) {
            User user = userService.getUserInfo(userid);
            session.setAttribute("userName", user.getName());
            session.setAttribute("userEmail", user.getEmail());
            session.setAttribute("userPhone", user.getPhone());
            session.setAttribute("userAddress", user.getAddress());
            session.setAttribute("userBirth", user.getBirth());
            session.setAttribute("userCreateDate", user.getCreateDate());
        } else {
            Seller seller = sellerService.getSellerInfo(userid);
            session.setAttribute("userName", seller.getStores().getCeo());
            session.setAttribute("userEmail", seller.getEmail());
            session.setAttribute("userPhone", seller.getStores().getTel());
            session.setAttribute("userAddress", seller.getStores().getAddr());
            session.setAttribute("userCreateDate", seller.getCreateDate());
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

    @PostMapping("/isExist/id")
    @ResponseBody
    public boolean isExistId(@RequestBody UserDTO userDTO) {
        String id = userDTO.getUserid();
        System.out.println("id =========== " + id);
        return userService.isExistId(id);
    }

    @PostMapping("/changeAddress")
    @ResponseBody
    public Map<String, Object> changeAddress(@RequestBody User userDTO, HttpSession session) {
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
}
