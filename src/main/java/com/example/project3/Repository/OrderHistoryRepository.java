package com.example.project3.Repository;

import com.example.project3.Entity.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {
    List<OrderHistory> findByIdOrderByOrderDateDesc(String id);
}
