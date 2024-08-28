package com.example.project3.Service;

import com.example.project3.DTO.StoreDetailsDTO;
import com.example.project3.Entity.Stores;
import com.example.project3.Repository.StoresRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
public class StoreDetailsServiceImpl implements StoreDetailsService {

    private final StoresRepository repository;

    @Override
    @Transactional
    public StoreDetailsDTO showStore(Long sno) {
        Optional<Stores> result = repository.findById(sno);
        return result.isPresent() ? entityToDto(result.get()) : null;
    }

    @Override
    public List<StoreDetailsDTO> searchStore(String searchText) {
        List<Stores> stores = repository.searchStores(searchText);
        return stores.stream()
                .map(this::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void increaseOrderCount(String store) {
        repository.incrementCounterByStore(store);
    }

    @Override
    public boolean isExistStore(String store) {
        boolean result = repository.findByStore(store) != null;
        return result;
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
}