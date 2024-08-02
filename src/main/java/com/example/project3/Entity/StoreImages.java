package com.example.project3.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Store_Images")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StoreImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sno")
    private Stores store;

    @Column(nullable = false)
    private String imageUrl;
}
