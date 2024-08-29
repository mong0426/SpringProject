package com.example.project3.DTO;

import com.example.project3.Entity.Stores;
import lombok.Data;

@Data
public class StoreImagesDTO {
    private Long id;
    private String imageUrl;
    private Stores stores;
}
