package com.post.repository;

import com.post.app.repository.member.MemberRepository;
import com.post.app.domain.member.Address;
import com.post.app.domain.member.Member;
import com.post.app.exception.NotFoundMemberException;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.post.app.domain.member.Role.USER;
import static org.assertj.core.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class MemberRepositoryImplTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    @Order(1)
    @DisplayName("회원을 저장하면 저장된 회원의 정보가 반환된다")
    void saveMember() {
        // given
        Member member = createMember();

        //when
        Member saveMember = memberRepository.save(member);

        //then
        assertThat(saveMember).isEqualTo(member);
    }

    @Test
    @Order(2)
    @DisplayName("저장한 회원과 조회한 회원의 정보는 같아야한다")
    void findById() {
        // given
        Member member = createMember();
        Member saveMember = memberRepository.save(member);

        //when
        Member findMember = memberRepository.findById(saveMember.getId());

        //then
        assertThat(saveMember).isEqualTo(findMember);
    }

    @Test
    @Order(3)
    @DisplayName("저장된 회원이 3명일 경우 List의 size는 3이어야한다.")
    void findMemberList() {
        // given
        Member memberA = Member.builder()
                .loginId("memberA")
                .password("1234")
                .email("memberA@example.com")
                .build();

        Member memberB = Member.builder()
                .loginId("memberB")
                .password("1234")
                .email("memberB@example.com")
                .build();

        Member memberC = Member.builder()
                .loginId("memberC")
                .password("1234")
                .email("memberC@example.com")
                .build();

        memberRepository.save(memberA);
        memberRepository.save(memberB);
        memberRepository.save(memberC);

        //when
        List<Member> memberList = memberRepository.findMemberList();

        //then
        assertThat(memberList).hasSize(3);
    }

    @Test
    @Order(4)
    @DisplayName("조회하고자하는 id의 회원이 없으면 NotFoundMemberException 예외가 발생한다")
    void findByIdWithNonExistId() {
        // given
        Member member = createMember();
        Member saveMember = memberRepository.save(member);

        //when
        Long findId = 999L;

        //then
        assertThat(saveMember.getId()).isEqualTo(member.getId());
        assertThatThrownBy(() -> memberRepository.findById(findId)).isInstanceOf(NotFoundMemberException.class);
    }

    @Test
    @Order(5)
    @DisplayName("저장한 회원과 name필드를 통해 조회한 회원의 정보는 같아야한다")
    void findByName() {
        // given
        Member member = createMember();
        Member saveMember = memberRepository.save(member);

        //when
        Member findMember = memberRepository.findByName(member.getName());

        //then
        assertThat(saveMember).isEqualTo(findMember);
    }

    @Test
    @Order(6)
    @DisplayName("조회하고자하는 이름의 회원이 없으면 EmptyResultDataAccessException 예외가 발생한다")
    void findByNameWithNonExistName() {
        // given
        Member member = createMember();
        Member saveMember = memberRepository.save(member);
        Member findMember = memberRepository.findByName("John Doe");

        //when
        String noMemberName = "I am Not Member";

        //then
        assertThat(findMember.getName()).isEqualTo(saveMember.getName());

        assertThatThrownBy(() -> memberRepository.findByName(noMemberName)).isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
    @Order(7)
    @DisplayName("삭제된 회원에 대한 조회시 조회에 실패해야한다.")
    void delete() {
        // given
        Member member = createMember();
        Member saveMember = memberRepository.save(member);

        Member findMember = memberRepository.findById(saveMember.getId());
        assertThat(findMember).isEqualTo(saveMember);

        //when
        memberRepository.delete(saveMember.getId());

        //then
        assertThatThrownBy(() -> memberRepository.findById(saveMember.getId())).isInstanceOf(NotFoundMemberException.class);
    }

    @Test
    @Order(8)
    @DisplayName("중복되는 이메일로 회원가입시 TRUE를 반환한다")
    void isExistEmail() {
        // given
        Member member = createMember();
        memberRepository.save(member);

        //when
        boolean result = memberRepository.isExistEmail("john.doe@example.com");

        //then
        assertThat(result).isTrue();
    }

    @Test
    @Order(9)
    @DisplayName("중복되는 아이디로 회원가입시 TRUE를 반환한다")
    void isExistLoginId() {
        // given
        Member member = createMember();
        memberRepository.save(member);

        //when
        boolean result = memberRepository.isExistLoginId("user1");

        //then
        assertThat(result).isTrue();
    }

    @Test
    @Order(10)
    @DisplayName("중복되는 name 또는 loginId로 조회시 TRUE를 반환한다")
    void isDuplicateName() {
        // given
        Member member = Member.builder()
                .loginId("memberA")
                .password("1234")
                .name("멤버A")
                .build();
        memberRepository.save(member);

        //name 중복
        Member duplicateNameMember = Member.builder()
                .loginId("memberB")
                .password("4321")
                .name("멤버A")
                .build();

        //loginId 중복
        Member duplicateLoginIdMember = Member.builder()
                .loginId("memberA")
                .password("5432")
                .name("멤버C")
                .build();

        //loginId - name 중복
        Member duplicateNameLoginId = Member.builder()
                .loginId("memberC")
                .password("3214")
                .name("memberA")
                .build();

        //when
        boolean duplicateName = memberRepository.isDuplicateName(duplicateNameMember.getName());

        boolean duplicateLoginId = memberRepository.isDuplicateName(duplicateLoginIdMember.getLoginId());

        boolean duplicateIdName = memberRepository.isDuplicateName(duplicateNameLoginId.getName());
        //then
        assertThat(duplicateName).isTrue();
        assertThat(duplicateLoginId).isTrue();
        assertThat(duplicateIdName).isTrue();
    }

    private Member createMember() {
        return Member.builder()
                .loginId("user1")
                .password("password1")
                .name("John Doe")
                .email("john.doe@example.com")
                .address(new Address("A city", "A street", "A zipcode"))
                .birthdate(LocalDate.of(1990, 1, 1))
                .phone("010-1234-5678")
                .role(USER)
                .createAt(LocalDateTime.now())
                .build();
    }
}