package com.example.project3.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reviews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sno")
    private Stores store;

    @Column(nullable = false)
    private double rating;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String writer;

}
