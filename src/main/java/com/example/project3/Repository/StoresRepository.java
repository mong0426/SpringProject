package com.example.project3.Repository;

import com.example.project3.Entity.Stores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoresRepository extends JpaRepository<Stores, Long> {

    @Query("SELECT DISTINCT s FROM Stores s " +
            "LEFT JOIN s.foods f " +
            "WHERE LOWER(s.store) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
            "OR LOWER(f.food) LIKE LOWER(CONCAT('%', :searchText, '%')) " +
            "OR LOWER(f.description) LIKE LOWER(CONCAT('%', :searchText, '%'))")
    List<Stores> searchStores(@Param("searchText") String searchText);

    @Modifying
    @Query("UPDATE Stores s SET s.orderCount = s.orderCount + 1 WHERE s.store = :store")
    void incrementCounterByStore(@Param("store") String store);

    Stores findByStore(String store);
}
