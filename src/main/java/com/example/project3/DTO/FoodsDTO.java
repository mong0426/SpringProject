package com.example.project3.DTO;

import com.example.project3.Entity.Stores;
import lombok.Data;

@Data
public class FoodsDTO {
    private Long fno;
    private String food;
    private String description;
    private Integer price;
    private String imageUrl;
    private Stores store;
}
