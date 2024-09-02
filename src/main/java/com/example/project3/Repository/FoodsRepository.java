package com.example.project3.Repository;

import com.example.project3.Entity.Foods;
import com.example.project3.Entity.Stores;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodsRepository extends JpaRepository<Foods, Long> {
    Foods findByStoreAndFood(Stores store, String food);
}
