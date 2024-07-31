//package com.example.project3.entity;
//
//import com.example.project3.Entity.UserRole;
//import jakarta.persistence.*;
//import lombok.*;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.time.LocalDate;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.Set;
//
//@Entity
//@Getter
//@Setter
//@Builder
//@AllArgsConstructor
//@Table(name = "USERS")
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class Users implements UserDetails {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false, unique = true, length = 50)
//    private String username;
//
//    @Column(nullable = false, unique = true, length = 50)
//    private String email;
//
//    @Column(nullable = false, length = 30)
//    private String nickName;
//
//    @Column(nullable = false, length = 100)
//    private String password;
//
//    @Column(nullable = false, length = 15)
//    private String phoneNum;
//
//    @Column(nullable = false, length = 100)
//    private String address;
//
//    @Column(nullable = false)
//    private LocalDate createdAt = LocalDate.now();
//
//    @Enumerated(EnumType.STRING)
//    @Column(nullable = false)
//    private UserRole role;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Set.of(role);
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
