package com.post.api.member.request;

import com.post.domain.member.Address;
import com.post.domain.member.Role;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class MemberCreateRequest {

    private String loginId;
    private String password;
    private String name;
    private String email;
    private Address address;
    private LocalDate birth;
    private String phone;
    private Role role;
    private LocalDate createAt;
}
