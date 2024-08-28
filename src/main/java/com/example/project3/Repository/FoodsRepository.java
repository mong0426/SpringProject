package com.example.project3.Repository;

import com.example.project3.Entity.Foods;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodsRepository extends JpaRepository<Foods, Long> {
}
