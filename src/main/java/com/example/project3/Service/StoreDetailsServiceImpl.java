package com.example.project3.Service;

import com.example.project3.DTO.PageableReviewsDTO;
import com.example.project3.DTO.ReviewsDTO;
import com.example.project3.DTO.StoreDetailsDTO;
import com.example.project3.Entity.Foods;
import com.example.project3.Entity.Reviews;
import com.example.project3.Entity.Stores;
import com.example.project3.Repository.FoodsRepository;
import com.example.project3.Repository.ReviewsRepository;
import com.example.project3.Repository.StoresRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class StoreDetailsServiceImpl implements StoreDetailsService {

    private final StoresRepository repository;
    private final ReviewsRepository reviewsRepository;
    private final FoodsRepository foodsRepository;

    @Override
    @Transactional
    public StoreDetailsDTO showStore(Long sno) {
        Optional<Stores> result = repository.findById(sno);
        return result.map(this::entityToDto).orElse(null);
    }

    @Override
    @Transactional
    public Page<Stores> searchStore(String searchText, String sort, String sortDirection, Integer deliveryTip, Integer minOrder, Integer orderCount, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (sort != null && sortDirection != null) {
            Sort sortResult = Sort.by(Sort.Direction.fromString(sortDirection), sort);
            pageable = PageRequest.of(page, size, sortResult);
        }
        return repository.searchStores(searchText, minOrder, deliveryTip, orderCount, pageable);
    }

    @Override
    @Transactional
    public void increaseOrderCount(String store) {
        repository.incrementCounterByStore(store);
    }

    @Override
    @Transactional
    public void increaseLikesBySno(Long sno, Integer value) {
        repository.increaseLikesBySno(sno, value);
    }

    @Override
    public boolean isExistStore(String store) {
        return repository.findByStore(store) != null;
    }

    @Override
    public void changeStoreInfo(StoreDetailsDTO storeDetailsDTO) {
        Long sno = storeDetailsDTO.getSno();
        Optional<Stores> existingStoreOptional = repository.findById(sno);

        if (existingStoreOptional.isPresent()) {
            Stores existingStore = existingStoreOptional.get();

            // 기존 엔티티의 필드를 DTO에서 제공한 값으로 업데이트합니다.
            existingStore.setSno(storeDetailsDTO.getSno());
            existingStore.setStore(storeDetailsDTO.getStore());
            existingStore.setCeo(storeDetailsDTO.getCeo());
            existingStore.setOpeningHours(storeDetailsDTO.getOpeningHours());
            existingStore.setClosedDays(storeDetailsDTO.getClosedDays());
            existingStore.setDeliTime(storeDetailsDTO.getDeliTime());
            existingStore.setDeliTip(storeDetailsDTO.getDeliTip());
            existingStore.setMinOrder(storeDetailsDTO.getMinOrder());
            existingStore.setTel(storeDetailsDTO.getTel());
            existingStore.setAddr(storeDetailsDTO.getAddr());
            // 업데이트된 엔티티를 저장합니다.
            repository.save(existingStore);
        } else {
            throw new EntityNotFoundException("Store not found with ID: " + sno);
        }
    }

    @Override
    public Stores findBySno(Long sno) {
        return repository.findById(sno).orElse(null);
    }

    @Override
    public PageableReviewsDTO getReviews(Stores store, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Reviews> reviews = reviewsRepository.findAllByStore(store, pageable);
        List<ReviewsDTO> reviewsDTO = reviews.stream()
                .map(review -> new ReviewsDTO(
                        review.getRno(),
                        review.getStore().getStore(),
                        review.getFood().getFood(),
                        review.getRating(),
                        review.getTitle(),
                        review.getContent(),
                        review.getWriter()))
                .collect(Collectors.toList());
        return new PageableReviewsDTO(
                reviewsDTO,
                reviews.getTotalPages(),
                reviews.getTotalElements(),
                reviews.getNumber());
    }

    @Override
    public Stores findStoresByStore(String storeName) {
        return repository.findByStore(storeName);
    }

    @Override
    public void registerReview(ReviewsDTO reviewsDTO) {
        // DTO를 엔티티로 변환
        Stores store = repository.findByStore(reviewsDTO.getStoreName());
        Foods food = foodsRepository.findByStoreAndFood(store, reviewsDTO.getFoodName());
        Reviews review = new Reviews(
                reviewsDTO.getRno(),
                store,
                food,
                reviewsDTO.getRating(),
                reviewsDTO.getTitle(),
                reviewsDTO.getContent(),
                reviewsDTO.getWriter()
        );
        // 엔티티를 데이터베이스에 저장
        reviewsRepository.save(review);
    }

    @Override
    public List<Double> getAverageRatings(Page<Stores> storesPage) {
        List<Long> storeSno = storesPage.stream()
                .map(Stores::getSno)
                .collect(Collectors.toList());
        List<Object[]> results = reviewsRepository.findAverageRatingsByStoreSno(storeSno);
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        return results.stream()
                .map(row -> {
                    Double rating = (Double) row[1];
                    if (row[1] == null) {
                        rating = 0.0;
                    }
                    return Double.parseDouble(decimalFormat.format(rating));
                })
                .toList();
    }

    @Override
    public List<Stores> findAllByStoreAndFood(String category) {
        return repository.findAllByStoreAndFood(category);
    }
}