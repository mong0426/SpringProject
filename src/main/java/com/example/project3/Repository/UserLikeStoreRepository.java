package com.example.project3.Repository;

import com.example.project3.Entity.UserLikeStore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserLikeStoreRepository extends JpaRepository<UserLikeStore, Long> {
    Optional<UserLikeStore> findByIdAndSno(String id, Long sno);
}
