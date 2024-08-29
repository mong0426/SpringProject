package com.example.project3.Service;

import com.example.project3.DTO.UserDTO;
import com.example.project3.Entity.User;
// Role Enum을 가져옵니다
import com.example.project3.Entity.UserLikeStore;
import com.example.project3.Repository.SellerRepository;
import com.example.project3.Repository.UserLikeStoreRepository;
import com.example.project3.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserLikeStoreRepository userLikeStoreRepository;

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

    public boolean IsLikeStore(String id, Long sno) {
        return userLikeStoreRepository.findByIdAndSno(id, sno).isPresent();
    }

    public void changeLikes(String id, Long sno) {
        Optional<UserLikeStore> userLikeStoreOptional = userLikeStoreRepository.findByIdAndSno(id, sno);

        if (userLikeStoreOptional.isPresent()) {
            // 좋아요 상태가 이미 있는 경우: 삭제
            userLikeStoreRepository.delete(userLikeStoreOptional.get());
        } else {
            // 좋아요 상태가 없는 경우: 추가
            UserLikeStore newLike = new UserLikeStore();
            newLike.setId(id);
            newLike.setSno(sno);
            newLike.setLikes("true"); // 좋아요 상태를 true로 설정
            userLikeStoreRepository.save(newLike);
        }
    }
}