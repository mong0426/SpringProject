package com.example.project3.Service;

import com.example.project3.DTO.SellerDTO;
import com.example.project3.Entity.Seller;

public interface SellerService {

    Seller getSellerInfo(String userid);

    void createStoreAndSeller(SellerDTO sellerDTO);

    void deleteSeller(String id);
}
