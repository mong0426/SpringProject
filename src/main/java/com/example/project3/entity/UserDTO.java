package com.example.project3.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserDTO {
    @Id
    @GeneratedValue
    private Long id; //자동생성 아이디
    private String name; //이름
    private String username; //아이디
    private String password; //비번
    private String email; //이메일
    private String phone; //휴대폰번호
    private String address; //주소
    private String gender; //성별
    private String birthday; //생일
    private String role; //권한

}
