package com.post.domain.embeded;

import jakarta.persistence.Embeddable;

@Embeddable
public class Address {

    /**
     * ex
     * 서울시 강남구
     * 테헤란로
     *
     */
    private String city;
    private String street;
    private String zipcode;
}
