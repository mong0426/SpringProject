package com.example.project3.Entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String birth;

//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false) // NOT NULL 설정
//    private Role role;

    @Column(nullable = false)
    private LocalDateTime createDate;

    @Builder
    public User(String username, String name, String password, String email, String address, String phone,
                String birth, LocalDateTime createDate) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.email = email;
        this.address = address;
        this.phone = phone;
        this.birth = birth;
//        this.role = role != null ? role : Role.USER; // Role 기본값 설정
        this.createDate = createDate != null ? createDate : LocalDateTime.now(); // createDate 기본값 설정
    }

//    public enum Role {
//        USER,
//        ADMIN
//    }
}
