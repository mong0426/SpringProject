package com.example.project3.Entity;

import jakarta.persistence.*;

@Entity
public class StoreImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sno")
    private Stores store;

    @Column(nullable = false)
    private String imageUrl;
}
