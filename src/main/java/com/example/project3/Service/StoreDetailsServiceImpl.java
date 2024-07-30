package com.example.project3.Service;

import com.example.project3.DTO.StoreDetailsDTO;
import com.example.project3.Entity.Stores;
import com.example.project3.Repository.StoresRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Log4j2
@RequiredArgsConstructor
public class StoreDetailsServiceImpl implements StoreDetailsService {

    private final StoresRepository repository;

    @Override
    public StoreDetailsDTO showStore(Long sno) {
        Optional<Stores> result = repository.findById(sno);

        return result.isPresent() ? entityToDto(result.get()) : null;
    }
}
