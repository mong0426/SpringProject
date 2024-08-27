package com.example.project3.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ono;

    @Column(nullable = false)
    private String id;

    @Column(nullable = false)
    private String store;

    @Column(nullable = false)
    private String food;

    @Column(nullable = false)
    private Integer totalPrice;

    @Column(nullable = false)
    private LocalDateTime orderDate;

    @Column
    @Builder.Default
    private String reviewed = "N";
}
