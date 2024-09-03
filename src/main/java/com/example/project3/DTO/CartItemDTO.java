package com.example.project3.DTO;

import lombok.Data;

@Data
public class CartItemDTO {

    private String storeName;
    private String foodName;
    private int quantity;
    private String foodDesc;
    private int price;
    private String imgSrc;
    private Integer deliTip;
}
