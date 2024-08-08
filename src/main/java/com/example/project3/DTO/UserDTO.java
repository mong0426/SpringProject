//package com.example.project3.DTO;
//
//import com.example.project3.Entity.User;
//import jakarta.validation.constraints.Email;
//import jakarta.validation.constraints.NotBlank;
//import jakarta.validation.constraints.Pattern;
//import jakarta.validation.constraints.Size;
//import lombok.*;
//
//import javax.management.relation.Role;
//
//@Data
//@Getter
//@Builder
//public class UserDTO {
//    @NotBlank(message = "아이디를 입력해주세요.")
//    @Size(min = 2, max = 10, message = "아이디는 2자 이상 10자 이하로 입력해주세요.")
//    private String username;
//
//    @NotBlank(message = "비밀번호를 입력해주세요.")
//    @Size(min = 8, max = 20, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
//    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}", message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
//    private String password;
//
//    @NotBlank(message = "이메일 주소를 입력해주세요.")
//    @Email(message = "올바른 이메일 주소를 입력해주세요.")
//    private String email;
//    private Role role;
//    private String name;
//    private String address;
//
//    @NotBlank(message = "휴대폰 번호를 입력해주세요.")
//    @Pattern(regexp = "(01[016789])(\\d{3,4})(\\d{4})", message = "올바른 휴대폰 번호를 입력해주세요.")
//    private String phone;
//
//    private String gender;
//    private String birth;
//    private String createDate;
//
//}
