package com.example.project3.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserDTO {

    private Long id;

    @NotBlank(message = "아이디를 입력해주세요.")
    @Size(min = 6, max = 12, message = "아이디는 영문 혹은 숫자를 사용해 6자 이상 12자 이하로 입력해주세요.")
    private String userid;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Size(min = 8, max = 14, message = "비밀번호는 8자 이상 20자 이하로 입력해주세요.")
    @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", message = "비밀번호는 8~20자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
    private String password;

    @Pattern(regexp = "^(?![ㄱ-ㅎ]+$)(?![ㅏ-ㅣ]+$)[가-힣]{2,6}$", message = "올바른 이름을 입력해주세요.")
    @NotBlank(message = "이름을 입력해주세요.")
    private String name;

    @NotBlank(message = "이메일 주소를 입력해주세요.")
    @Email(message = "올바른 이메일 주소를 입력해주세요.")
    private String email;

    @NotBlank(message = "휴대폰 번호를 입력해주세요.")
    @Pattern(regexp = "(01[016789])[-]?\\d{3,4}[-]?\\d{4}", message = "올바른 휴대폰 번호를 입력해주세요.")
    private String phone;

    private String address;

    private String birth; // 날짜 형식이 필요하다면 LocalDate로 변경

    private String createDate; // 날짜 형식이 필요하다면 LocalDate로 변경
}
