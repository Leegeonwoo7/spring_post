package com.post.service.member;

import com.post.domain.member.Address;
import com.post.domain.member.Member;
import com.post.exception.ExistMemberEmailException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.post.domain.member.Role.ADMIN;
import static com.post.domain.member.Role.USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("중복되지 않는 이메일, 아이디로 회원가입시 회원가입에 성공한다")
    @Transactional
    void join() {
        // given
        Member member = createMember();

        //when
        Member joinMember = memberService.join(member);

        //then
        assertThat(joinMember).isNotNull();
        assertThat(joinMember.getId()).isNotNull();
    }

    @Test
    @DisplayName("중복되는 이메일로 회원가입시 회원가입에 실패한다.")
    @Transactional
    void duplicateEmailJoinMember() {
        // given
        Member member = createMember();
        memberService.join(member);

        //when
        Member duplicateMember = createDuplicateEmailMember();

        //then
        assertThatThrownBy(() -> memberService.join(duplicateMember))
                .isInstanceOf(ExistMemberEmailException.class);
    }

    private Member createMember() {
        return Member.builder()
                .loginId("user1")
                .password("password1")
                .name("John Doe")
                .email("john.doe@example.com")
                .address(new Address("A city", "A street", "A zipcode"))
                .birthdate(LocalDateTime.of(1990, 1, 1, 0, 0))
                .phone("010-1234-5678")
                .role(USER)
                .createAt(LocalDateTime.now())
                .build();
    }

    private Member createDuplicateEmailMember() {
        return Member.builder()
                .loginId("user2")
                .password("password2")
                .name("Jane Doe")
                .email("john.doe@example.com")
                .address(new Address("B city", "B street", "B zipcode"))
                .birthdate(LocalDateTime.of(1985, 5, 15, 0, 0))
                .phone("010-9876-5432")
                .role(ADMIN)
                .createAt(LocalDateTime.now())
                .build();
    }
}