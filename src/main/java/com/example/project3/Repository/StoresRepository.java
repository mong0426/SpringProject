package com.example.project3.Repository;

import com.example.project3.Entity.Stores;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoresRepository extends JpaRepository<Stores, Long> {

    @Query("SELECT DISTINCT s FROM Stores s " +
            "LEFT JOIN s.foods f " +
            "WHERE (LOWER(s.store) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
            "OR LOWER(f.food) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
            "OR LOWER(f.description) LIKE LOWER(CONCAT('%', :searchText, '%'))) " +
            "AND (:minOrder IS NULL OR s.minOrder <= :minOrder) " +
            "AND (:deliTip IS NULL OR s.deliTip <= :deliTip) " +
            "AND (:orderCount IS NULL OR s.orderCount <= :orderCount)"
    )
    Page<Stores> searchStores(@Param("searchText") String searchText,
                              @Param("minOrder") Integer minOrder,
                              @Param("deliTip") Integer deliTip,
                              @Param("orderCount") Integer orderCount,
                              Pageable pageable);

    @Modifying
    @Query("UPDATE Stores s SET s.orderCount = s.orderCount + 1 WHERE s.store = :store")
    void incrementCounterByStore(@Param("store") String store);

    @Modifying
    @Query("UPDATE Stores s SET s.likes = s.likes + :value WHERE s.sno = :sno")
    void increaseLikesBySno(@Param("sno") Long sno, @Param("value") Integer value);

    Stores findByStore(String store);

    @Query("SELECT DISTINCT s FROM Stores s " +
            "LEFT JOIN s.foods f " +
            "WHERE (:category IS NULL " +
            "OR LOWER(s.store) LIKE LOWER(CONCAT('%', :category, '%')) " +
            "OR LOWER(f.food) LIKE LOWER(CONCAT('%', :category, '%')))")
    List<Stores> findAllByStoreAndFood(@Param("category") String category);
}
