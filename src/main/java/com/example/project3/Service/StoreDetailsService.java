package com.example.project3.Service;

import com.example.project3.DTO.StoreDetailsDTO;
import com.example.project3.Entity.Stores;

import java.util.List;

public interface StoreDetailsService {

    StoreDetailsDTO showStore(Long sno);

    List<StoreDetailsDTO> searchStore(String searchText);

    void IncreaseOrderCount(String store);

    default Stores dtoToEntity(StoreDetailsDTO dto) {
        Stores entity = Stores.builder()
                .sno(dto.getSno())
                .store(dto.getStore())
                .ceo(dto.getCeo())
                .tel(dto.getTel())
                .addr(dto.getAddr())
                .openingHours(dto.getOpeningHours())
                .closedDays(dto.getClosedDays())
                .minOrder(dto.getMinOrder())
                .deliTime(dto.getDeliTime())
                .deliTip(dto.getDeliTip())
                .likes(dto.getLikes())
                .orderCount(dto.getOrderCount())
                .images(dto.getImages())
                .foods(dto.getFoods())
                .reviews(dto.getReviews())
                .build();
        return entity;
    }

    default StoreDetailsDTO entityToDto(Stores entity) {

        StoreDetailsDTO dto = StoreDetailsDTO.builder()
                .sno(entity.getSno())
                .store(entity.getStore())
                .ceo(entity.getCeo())
                .tel(entity.getTel())
                .addr(entity.getAddr())
                .openingHours(entity.getOpeningHours())
                .closedDays(entity.getClosedDays())
                .minOrder(entity.getMinOrder())
                .deliTime(entity.getDeliTime())
                .deliTip(entity.getDeliTip())
                .likes(entity.getLikes())
                .orderCount(entity.getOrderCount())
                .images(entity.getImages())
                .foods(entity.getFoods())
                .reviews(entity.getReviews())
                .build();
        return dto;
    }
}
