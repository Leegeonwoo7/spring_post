package com.post.service.member;

import com.post.app.api.member.request.MemberCreateRequest;
import com.post.app.api.member.response.MemberResponse;
import com.post.app.service.MemberService;
import com.post.app.domain.member.Address;
import com.post.app.exception.ExistMemberEmailException;
import com.post.app.exception.ExistMemberLoginIdException;
import com.post.app.exception.ExistMemberNameException;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static com.post.app.domain.member.Role.ADMIN;
import static com.post.app.domain.member.Role.USER;
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
        MemberCreateRequest member = createMember();

        //when
        MemberResponse joinMember = memberService.join(member);

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
        MemberCreateRequest member = createMember();
        memberService.join(member);

        //when
        MemberCreateRequest duplicateMember = createDuplicateEmailMember();

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
        MemberCreateRequest memberA = MemberCreateRequest.builder()
                .loginId("memberA")
                .password("1234")
                .name("멤버A")
                .build();
        memberService.join(memberA);

        MemberCreateRequest memberB = MemberCreateRequest.builder()
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
        MemberCreateRequest memberA = MemberCreateRequest.builder()
                .password("1234")
                .loginId("memberA")
                .build();

        //when
        MemberResponse joinMember = memberService.join(memberA);
        MemberResponse findMember = memberService.findMemberById(joinMember.getId());

        //then
        assertThat(findMember.getName()).isEqualTo("memberA");
    }

    @Test
    @DisplayName("loginId와 name이 이미 존재하면 예외가 발생한다 ")
    @Order(4)
    @Transactional
    void duplicateLoginIdNameJoin() {
        // given
        MemberCreateRequest member = MemberCreateRequest.builder()
                .password("1234")
                .loginId("memberA")
                .name("회원A")
                .build();

        MemberCreateRequest duplicateMember = MemberCreateRequest.builder()
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
        MemberCreateRequest memberA = MemberCreateRequest.builder()
                .loginId("memberA")
                .password("1234")
                .build();
        MemberResponse joinMember = memberService.join(memberA);

        //when
        MemberResponse memberById = memberService.findMemberById(joinMember.getId());

        //then
        assertThat(joinMember).isEqualTo(memberById);
    }

    @Test
    @DisplayName("회원이름을 입력된 값으로 변경한다")
    @Order(6)
    @Transactional
    void changeName() {
        // given
        MemberCreateRequest memberA = MemberCreateRequest.builder()
                .loginId("memberA")
                .password("1234")
                .build();
        MemberResponse joinMember = memberService.join(memberA);

        String newName = "newName";

        //when
        memberService.changeName(joinMember.getId(), newName);
        MemberResponse updateMember = memberService.findMemberById(joinMember.getId());

        //then
        assertThat(updateMember.getName()).isEqualTo(newName);
    }

    @Test
    @DisplayName("모든회원을 조회한 Member엔티티는 MemberResponse로 변환되어야한다.")
    @Order(7)
    void findMemberList() {
        // given
        MemberCreateRequest memberA = MemberCreateRequest.builder()
                .loginId("memberA")
                .password("1234")
                .build();

        MemberCreateRequest memberB = MemberCreateRequest.builder()
                .loginId("memberB")
                .password("1234")
                .build();

        MemberCreateRequest memberC = MemberCreateRequest.builder()
                .loginId("memberC")
                .password("1234")
                .build();
        memberService.join(memberA);
        memberService.join(memberB);
        memberService.join(memberC);

        //when
        List<MemberResponse> memberList = memberService.findMemberList();

        //then
        memberList.forEach(memberResponse -> assertThat(memberResponse).isInstanceOf(MemberResponse.class));
        assertThat(memberList).hasSize(3);
    }

// 비밀번호 변경은 시큐리티 적용 필요성이 있기때문에 킵
//    @Test
//    @DisplayName("비밀번호를 입력된 값으로 변경한다")
//    @Order(6)
//    @Transactional
//    void changePassword() {
//        // given
//        MemberCreateRequest member = createMember();
//        MemberResponse saveMember = memberService.join(member);
//
//        String newPassword = "newPassword";
//
//        //when
//        memberService.changePassword(saveMember.getId(), newPassword);
//        MemberResponse updateMember = memberService.findMemberById(saveMember.getId());
//
//        //then
//        assertThat(updateMember.getPassword()).isEqualTo(newPassword);
//    }
    private MemberCreateRequest createMember() {
        return MemberCreateRequest.builder()
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

    private MemberCreateRequest createDuplicateEmailMember() {
        return MemberCreateRequest.builder()
                .loginId("user2")
                .password("password2")
                .name("Jane Doe")
                .email("john.doe@example.com")
                .address(new Address("B city", "B street", "B zipcode"))
                .birthdate(LocalDate.of(1985, 5, 15))
                .phone("010-9876-5432")
                .role(ADMIN)
                .createAt(LocalDateTime.now())
                .build();
    }
}