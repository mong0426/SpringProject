package com.example.project3.Service;

import com.example.project3.Entity.CustomUserDetails;
import com.example.project3.Entity.Seller;
import com.example.project3.Entity.User;
import com.example.project3.Repository.SellerRepository;
import com.example.project3.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SellerRepository sellerRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CustomUserDetails CustomUserDetails = null;
        User user = userRepository.findByUserid(username);
        if (user != null) {
            System.out.println("사용자 정보 있음");
            return new CustomUserDetails(
                    user.getUserid(),
                    user.getPassword()
            );
        } else {
            // 판매자 정보 조회
            Seller seller = sellerRepository.findById(username);
            if (seller != null) {
                System.out.println("판매자 정보 있음");
                return new CustomUserDetails(
                        seller.getId(),
                        seller.getPassword()
                );
            } else {
                //사용자와 판매자 모두 없으면 예외를 던짐
                System.out.println("사용자 또는 판매자 정보가 없습니다.");
                throw new UsernameNotFoundException("사용자 또는 판매자 정보가 없습니다.");
            }
        }
    }
}
