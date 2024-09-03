package com.example.project3.Repository;

import com.example.project3.Entity.Reviews;
import com.example.project3.Entity.Stores;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReviewsRepository extends JpaRepository<Reviews, Long> {
    Page<Reviews> findAllByStore(Stores store, Pageable pageable);

    @Query("SELECT s.sno, AVG(r.rating) " +
            "FROM Stores s LEFT JOIN s.reviews r " +
            "WHERE s.sno IN :storeSno " +
            "GROUP BY s.sno")
    List<Object[]> findAverageRatingsByStoreSno(@Param("storeSno") List<Long> storeSno);
}
