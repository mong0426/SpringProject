package com.example.project3.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Seller {

    @Id
    @Column(name = "id", updatable = false)
    private String id;

    @Column(nullable = false)
    private String password;

    @Column(name="email")
    private String email;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sno")
    private Stores stores;
}
