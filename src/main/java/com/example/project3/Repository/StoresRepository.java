package com.example.project3.Repository;

import com.example.project3.Entity.Stores;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoresRepository extends JpaRepository<Stores, Long> {

    @Query("SELECT * FROM Store s WHERE (:searchText IS NULL OR s.name LIKE %:searchText%) AND "
            + "(:deliveryTip IS NULL OR s.deliveryTip =< :deliveryTip) AND "
            + "(:rating IS NULL OR s.rating >= :rating) AND "
            + "(:minOrder IS NULL OR s.minOrder <= :minOrder)")
    List<Stores> searchStores(@Param("searchText") String searchText,
                            @Param("deliveryTip") String deliveryTip,
                            @Param("rating") String rating,
                            @Param("minOrder") String minOrder,
                            Pageable pageable);

    @Modifying
    @Query("UPDATE Stores s SET s.orderCount = s.orderCount + 1 WHERE s.store = :store")
    void incrementCounterByStore(@Param("store") String store);

    @Modifying
    @Query("UPDATE Stores s SET s.likes = s.likes + :value WHERE s.sno = :sno")
    void increaseLikesBySno(@Param("sno") Long sno, @Param("value") Integer value);

    Stores findByStore(String store);
}
