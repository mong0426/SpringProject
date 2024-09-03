package com.example.project3.DTO;

import com.example.project3.Entity.Foods;
import com.example.project3.Entity.Reviews;
import com.example.project3.Entity.StoreImages;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StoreDetailsDTO {

    private Long sno;
    private String store;
    private String ceo;
    private String tel;
    private String addr;
    private String openingHours;
    private String closedDays;
    private Integer minOrder;
    private String deliTime;
    private Integer deliTip;
    private Integer likes;
    private Integer orderCount;
    private List<StoreImages> images;
    private List<Foods> foods;
    private List<Reviews> reviews;

}
