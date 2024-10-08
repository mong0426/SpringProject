package com.example.project3.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Stores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sno;

    @Column(nullable = false, unique = true)
    private String store;

    @Column
    private String ceo;

    @Column
    private String tel;

    @Column
    private String addr;

    @Column
    private String openingHours;

    @Column
    private String closedDays;

    @Column
    private Integer minOrder;

    @Column
    private String deliTime;

    @Column
    private Integer deliTip;

    @Column
    private Integer likes;

    @Column
    private Integer orderCount;

    @OneToMany(mappedBy = "store", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoreImages> images;

    @OneToMany(mappedBy = "store", fetch = FetchType.EAGER,  cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Foods> foods;

    @OneToMany(mappedBy = "store", fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reviews> reviews;
}
