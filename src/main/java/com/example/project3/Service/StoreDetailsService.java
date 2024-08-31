package com.example.project3.Service;

import com.example.project3.DTO.PageableReviewsDTO;
import com.example.project3.DTO.ReviewsDTO;
import com.example.project3.DTO.StoreDetailsDTO;
import com.example.project3.Entity.Stores;

import java.util.List;

public interface StoreDetailsService {

    StoreDetailsDTO showStore(Long sno);

    List<StoreDetailsDTO> searchStore(String searchText);

    void increaseOrderCount(String store);

    void increaseLikesBySno(Long sno,Integer value);

    boolean isExistStore(String store);

    void changeStoreInfo(StoreDetailsDTO storeDetailsDTO);

    Stores findBySno(Long sno);

    default Stores dtoToEntity(StoreDetailsDTO dto) {
        return Stores.builder()
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
    }

    default StoreDetailsDTO entityToDto(Stores entity) {

        return StoreDetailsDTO.builder()
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
    }

    PageableReviewsDTO getReviews(Stores store,int page, int size);

    Stores findStoresByStore(String storeName);
}
