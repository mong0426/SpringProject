package com.example.project3.Service;

import com.example.project3.DTO.StoreImagesDTO;
import com.example.project3.Entity.StoreImages;
import com.example.project3.Repository.StoreImagesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StoreImageService {
    final StoreImagesRepository storeImagesRepository;

    public void uploadStoreImage(StoreImagesDTO storeImagesDTO) {
        StoreImages storeImages = new StoreImages();
        storeImages.setImageUrl(storeImagesDTO.getImageUrl());
        storeImages.setStore(storeImagesDTO.getStores());
        storeImagesRepository.save(storeImages);
    }
}
