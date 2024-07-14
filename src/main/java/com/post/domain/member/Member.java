package com.post.domain;

import com.post.domain.embeded.Address;
import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.GenerationType.*;

@Entity
@Getter
public class Member {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "member_id")
    private Long id;

//    @Column(nullable = false, length = 15)
    private String loginId;

//    @Column(nullable = false, length = 15)
    private String password;

    private String name;
    private String email;

    @Embedded
    private Address address;
}
