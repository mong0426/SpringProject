package com.example.project3.Service;

import com.example.project3.DTO.SellerDTO;
import com.example.project3.Entity.Seller;
import com.example.project3.Entity.Stores;
import com.example.project3.Repository.SellerRepository;
import com.example.project3.Repository.StoresRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Log4j2
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {
    @Autowired
    StoresRepository storesRepository;
    @Autowired
    SellerRepository sellerRepository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public void createStoreAndSeller(SellerDTO sellerDTO) {
        Stores store = new Stores();
        store.setStore(sellerDTO.getStore());
        store.setCeo(sellerDTO.getCeo());
        store.setTel(sellerDTO.getTel());
        store.setAddr(sellerDTO.getAddress());
        store.setOrderCount(0);
        store.setLikes(0);
        storesRepository.save(store);

        Seller seller = new Seller();
        seller.setId(sellerDTO.getId());
        seller.setPassword(passwordEncoder.encode(sellerDTO.getPassword()));
        seller.setEmail(sellerDTO.getEmail());
        seller.setStores(store);
        seller.setCreateDate(LocalDateTime.now());

        sellerRepository.save(seller);
    }

    @Override
    public Seller getSellerInfo(String userid){
        Seller seller = sellerRepository.findById(userid);
        return seller;
    }
}
