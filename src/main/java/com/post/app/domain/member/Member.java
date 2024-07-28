package com.post.domain.member;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static jakarta.persistence.EnumType.*;
import static jakarta.persistence.GenerationType.*;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, length = 15, unique = true)
    private String loginId;

    @Column(nullable = false, length = 15)
    private String password;

    @Column(length = 10, unique = true)
    private String name;
    private String email;

    @Embedded
    private Address address;
    private LocalDate birthdate;

    @Column(length = 15)
    private String phone;

    @Enumerated(STRING)
    private Role role;

    private LocalDateTime createAt;

    @Builder
    public Member(String loginId, String password, String name, String email, Address address, LocalDate birthdate, String phone, Role role, LocalDateTime createAt) {
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


    public void changeName(String name) {
        this.name = name;
    }
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }
}
