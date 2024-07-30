package com.example.project3.Repository;

import com.example.project3.Entity.Stores;
import com.example.project3.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoresRepository extends JpaRepository<Stores, Long> {
}
