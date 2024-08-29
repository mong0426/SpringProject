package com.example.project3.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserLikeStore {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long lno;

    @Column(nullable = false)
    private Long sno;

    @Column(nullable = false)
    private String likes = "false";

    @Column(nullable = false)
    private String id;
}
