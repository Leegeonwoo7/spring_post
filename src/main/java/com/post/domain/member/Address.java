package com.post.domain.member;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Embeddable
@NoArgsConstructor
@Getter
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

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
