package com.example.project3.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewsDTO {
    private Long rno;
    private String storeName;
    private String foodName;
    private double rating;
    private String title;
    private String content;
    private String writer;
}
