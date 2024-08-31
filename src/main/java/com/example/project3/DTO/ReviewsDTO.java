package com.example.project3.DTO;

import com.example.project3.Entity.Foods;
import com.example.project3.Entity.Stores;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ReviewsDTO {
    private Long rno;
    private String storeName;
    private String foodName;
    private double rating;
    private String title;
    private String content;
    private String writer;
}
