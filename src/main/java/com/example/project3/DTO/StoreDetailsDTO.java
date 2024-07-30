package com.example.project3.DTO;

import com.example.project3.Entity.StoreImage;
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
    private String minOrder;
    private String deliTime;
    private String deleTip;
    private Integer likes;
    private List<StoreImage> images;

}
