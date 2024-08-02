package com.example.project3.Service;

import com.example.project3.DTO.StoreDetailsDTO;
import com.example.project3.Entity.Stores;

public interface StoreDetailsService {

    StoreDetailsDTO showStore(Long sno);

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
                .images(dto.getImages())
                .foods(dto.getFoods())
                .build();
        return entity;
    }

    default StoreDetailsDTO entityToDto(Stores entity){

        StoreDetailsDTO dto  = StoreDetailsDTO.builder()
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
                .images(entity.getImages())
                .foods(entity.getFoods())
                .build();
        return dto;
    }
}