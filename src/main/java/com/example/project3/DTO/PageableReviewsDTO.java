package com.example.project3.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class PageableReviewsDTO {
    private List<ReviewsDTO> reviews;
    private int totalPages;
    private long totalElements;
    private int number;
}