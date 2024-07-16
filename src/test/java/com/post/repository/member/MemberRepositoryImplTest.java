package com.post.repository.member;

import com.post.domain.member.Address;
import com.post.domain.member.Member;
import com.post.domain.member.Role;
import com.post.exception.NotFoundMemberException;
import com.post.exception.ParameterException;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.post.domain.member.Role.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberRepositoryImplTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
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
    @DisplayName("조회하고자하는 id의 회원이 없으면 NotFoundMemberException 예외가 발생한다")
    void findByIdWithNonExistId() {
        // given
        Member member = createMember();
        Member saveMember = memberRepository.save(member);

        //when
        Long findId = 999L;

        //then
        assertThat(saveMember.getId()).isEqualTo(member.getId());
        assertThatThrownBy(() -> memberRepository.findById(findId))
                .isInstanceOf(NotFoundMemberException.class);
    }

    @Test
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

        assertThatThrownBy(() -> memberRepository.findByName(noMemberName))
                .isInstanceOf(EmptyResultDataAccessException.class);
    }

    @Test
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
        assertThatThrownBy(() -> memberRepository.findById(saveMember.getId()))
                .isInstanceOf(NotFoundMemberException.class);
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
}

//Member member2 = Member.builder()
//        .loginId("user2")
//        .password("password2")
//        .name("Jane Doe")
//        .email("jane.doe@example.com")
//        .address(new Address("B city", "B street", "B zipcode"))
//        .birthdate(LocalDateTime.of(1985, 5, 15, 0, 0))
//        .phone("010-9876-5432")
//        .role(ADMIN)
//        .createAt(LocalDateTime.now())
//        .build();
//
//Member member3 = Member.builder()
//        .loginId("user3")
//        .password("password3")
//        .name("Jim Beam")
//        .email("jim.beam@example.com")
//        .address(new Address("C city", "C street", "C zipcode"))
//        .birthdate(LocalDateTime.of(1995, 12, 31, 0, 0))
//        .phone("010-4567-8901")
//        .role(USER)
//        .createAt(LocalDateTime.now())
//        .build();

