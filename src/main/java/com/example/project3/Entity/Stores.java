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
    @GeneratedValue
    private Long sno;

    @Column(nullable = false)
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
    private String minOrder;

    @Column
    private String deliTime;

    @Column
    private String deliTip;

    @Column
    private Integer likes;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StoreImages> images;
}
