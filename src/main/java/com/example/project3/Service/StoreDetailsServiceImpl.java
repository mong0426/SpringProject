package com.example.project3.Service;

import com.example.project3.DTO.StoreDetailsDTO;
import com.example.project3.Entity.Stores;
import com.example.project3.Repository.StoresRepository;
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
    public void IncreaseOrderCount(String store) {
        repository.incrementCounterByStore(store);
    }
}
