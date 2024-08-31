package com.example.project3.Repository;

import com.example.project3.Entity.Reviews;
import com.example.project3.Entity.Stores;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewsRepository extends JpaRepository<Reviews, Long> {
    Page<Reviews> findAllByStore(Stores store, Pageable pageable);
}
