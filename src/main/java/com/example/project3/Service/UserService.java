package com.example.project3.Service;

import com.example.project3.DTO.UserDTO;
import com.example.project3.Entity.User;
// Role Enum을 가져옵니다
import com.example.project3.Repository.SellerRepository;
import com.example.project3.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;

    public User registerUser(UserDTO userDTO) {
//        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
//            throw new IllegalArgumentException("Email already exists.");
//        }
        // User 엔티티 생성
        User user = User.builder()
                .userid(userDTO.getUserid())
                .password(passwordEncoder.encode(userDTO.getPassword())) // 비밀번호 암호화
                .email(userDTO.getEmail())
                .name(userDTO.getName())
                .address(userDTO.getAddress())
                .phone(userDTO.getPhone())
                .birth(userDTO.getBirth())
                .createDate(LocalDateTime.now())
                .build();

        // 데이터베이스에 사용자 저장
        return userRepository.save(user);
    }

    public User getUserInfo(String id) {
        User user = userRepository.findByUserid(id);
        return user;
    }

    public boolean isExistId(String id) {
        System.out.println("Service id ========== " + id);
        boolean result = userRepository.findByUserid(id) != null;
        if (sellerRepository.findById(id) != null) {
            result = true;
        }
        return result;
    }

    public boolean isCoustomerUser(String id) {
        boolean result = userRepository.findByUserid(id) != null;
        return result;
    }

    public void updateAddress(String id, String address) {
        User user = userRepository.findByUserid(id);
        user.setAddress(address);
        userRepository.save(user);
    }
}
