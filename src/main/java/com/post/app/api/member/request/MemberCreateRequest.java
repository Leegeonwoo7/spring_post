package com.post.api.member.request;

import com.post.domain.member.Address;
import com.post.domain.member.Role;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
public class MemberCreateRequest {

    private String loginId;
    private String password;
    private String name;
    private String email;
    private Address address;
    private LocalDate birthdate;
    private String phone;
    private Role role;
    private LocalDateTime createAt;

    @Builder
    public MemberCreateRequest(String loginId, String password, String name, String email, Address address, LocalDate birthdate, String phone, Role role, LocalDateTime createAt) {
        this.loginId = loginId;
        this.password = password;
        this.name = name;
        this.email = email;
        this.address = address;
        this.birthdate = birthdate;
        this.phone = phone;
        this.role = role;
        this.createAt = createAt;
    }
}
