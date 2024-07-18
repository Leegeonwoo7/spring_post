package com.post.api.member.response;

import com.post.domain.member.Address;
import com.post.domain.member.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class MemberResponse {

    private String loginId;
    private String name;
    private String email;
    private Address address;
    private LocalDate birth;
    private String phone;
    private Role role;
    private LocalDate createAt;
}
