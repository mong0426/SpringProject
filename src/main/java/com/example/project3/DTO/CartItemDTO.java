package com.example.project3.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CartItemDTO {

    private String storeName;
    private String foodName;
    private int quantity;
    private String foodDesc;
    private int price;
    private String imgSrc;
}
