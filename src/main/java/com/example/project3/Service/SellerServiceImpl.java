package com.example.project3.Service;

import com.example.project3.DTO.SellerDTO;
import com.example.project3.DTO.StoreDetailsDTO;
import com.example.project3.Entity.Seller;
import com.example.project3.Entity.Stores;
import com.example.project3.Repository.SellerRepository;
import com.example.project3.Repository.StoresRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        storesRepository.save(store);

        Seller seller = new Seller();
        seller.setId(sellerDTO.getId());
        seller.setPassword(passwordEncoder.encode(sellerDTO.getPassword()));
        seller.setEmail(sellerDTO.getEmail());
        seller.setStores(store);

        sellerRepository.save(seller);
    }
}
