package com.post.service.member;

import com.post.domain.member.Address;
import com.post.domain.member.Member;
import com.post.exception.ExistMemberEmailException;
import com.post.exception.ExistMemberLoginIdException;
import com.post.exception.ExistMemberNameException;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.post.domain.member.Role.ADMIN;
import static com.post.domain.member.Role.USER;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    EntityManager em;

    @Test
    @DisplayName("중복되지 않는 이메일, 아이디로 회원가입시 회원가입에 성공한다")
    @Order(1)
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
    @Order(2)
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

    @Test
    @DisplayName("중복되는 아이디로 회원가입시 회원가입에 실패한다")
    @Order(3)
    @Transactional
    void duplicateLoginIdJoinMember() {
        // given
        Member memberA = Member.builder()
                .loginId("memberA")
                .password("1234")
                .name("멤버A")
                .build();
        memberService.join(memberA);

        Member memberB = Member.builder()
                .loginId("memberA")
                .password("4567")
                .name("멤버B")
                .build();

        //when
        assertThatThrownBy(() -> memberService.join(memberB))
                .isInstanceOf(ExistMemberLoginIdException.class);

        //then
        assertThat(memberA.getLoginId()).isEqualTo(memberB.getLoginId());
        assertThat(memberA).isNotEqualTo(memberB);
    }

    @Test
    @DisplayName("name이 NULL이면 loginId가 name으로 저장된다")
    @Order(4)
    @Transactional
    void nameNullJoin() {
        // given
        Member memberA = Member.builder()
                .password("1234")
                .loginId("memberA")
                .build();

        //when
        Member joinMember = memberService.join(memberA);
        Member findMember = memberService.findMemberById(joinMember.getId());

        //then
        assertThat(findMember.getName()).isEqualTo("memberA");
    }

    @Test
    @DisplayName("loginId와 name이 이미 존재하면 예외가 발생한다 ")
    @Order(4)
    @Transactional
    void duplicateLoginIdNameJoin() {
        // given
        Member member = Member.builder()
                .password("1234")
                .loginId("memberA")
                .name("회원A")
                .build();

        Member duplicateMember = Member.builder()
                .password("5678")
                .loginId("memberB")
                .name("memberA")
                .build();

        memberService.join(member);

        //when
        //then
        assertThatThrownBy(() -> memberService.join(duplicateMember))
                .isInstanceOf(ExistMemberNameException.class);

    }

    @Test
    @DisplayName("id필드로 회원을 조회한다")
    @Order(5)
    @Transactional
    void findMemberById() {
        // given
        Member memberA = Member.builder()
                .loginId("memberA")
                .password("1234")
                .build();
        Member joinMember = memberService.join(memberA);

        //when
        Member memberById = memberService.findMemberById(joinMember.getId());

        //then
        assertThat(joinMember).isEqualTo(memberById);
    }

    @Test
    @DisplayName("비밀번호를 입력된 값으로 변경한다")
    @Order(6)
    @Transactional
    void changePassword() {
        // given
        Member member = createMember();
        Member saveMember = memberService.join(member);

        String newPassword = "newPassword";

        //when
        memberService.changePassword(saveMember.getId(), newPassword);
        Member updateMember = memberService.findMemberById(saveMember.getId());

        //then
        assertThat(updateMember.getPassword()).isEqualTo(newPassword);
    }

    @Test
    @DisplayName("회원이름을 입력된 값으로 변경한다")
    @Order(7)
    @Transactional
    void changeName() {
        // given
        Member memberA = Member.builder()
                .loginId("memberA")
                .password("1234")
                .build();
        Member joinMember = memberService.join(memberA);

        String newName = "newName";

        //when
        memberService.changeName(joinMember.getId(), newName);
        Member updateMember = memberService.findMemberById(joinMember.getId());

        //then
        assertThat(updateMember.getName()).isEqualTo(newName);
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