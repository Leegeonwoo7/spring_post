package com.post.domain.member;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    /**
     * ex
     * 서울시 강남구 역삼동
     * 테헤란로
     * 113 XXX
     */
    private String city;
    private String street;
    private String zipcode;
}
