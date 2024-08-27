package com.example.project3.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderHistoryDTO {
    private Long ono;
    private String id;
    private String store;
    private String food;
    private String quantity;
    private Integer totalPrice;
    private LocalDateTime orderDate;
    private String Reviewed;
    private String formattedOrderDate;
}
