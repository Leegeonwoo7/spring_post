package com.post.config;

import com.post.domain.member.Address;
import com.post.domain.member.Member;
import com.post.domain.member.Role;
import com.post.repository.member.MemberRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final MemberRepositoryImpl memberRepository;

    @EventListener
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event){
        insertTestData();
    }

    private void insertTestData() {
        Member member1 = Member.builder()
                .loginId("john_doe")
                .password("1234")
                .name("John Doe")
                .email("john.doe@example.com")
                .address(new Address("A city", "A street", "A zipcode"))
                .birthdate(LocalDate.of(1990, 1, 1))
                .phone("010-1234-5678")
                .role(Role.USER)
                .createAt(LocalDateTime.now())
                .build();

        Member member2 = Member.builder()
                .loginId("jane_doe")
                .password("5678")
                .name("Jane Doe")
                .email("jane.doe@example.com")
                .address(new Address("B city", "B street", "B zipcode"))
                .birthdate(LocalDate.of(1992, 2, 2))
                .phone("010-8765-4321")
                .role(Role.ADMIN)
                .createAt(LocalDateTime.now())
                .build();

        memberRepository.save(member1);
        memberRepository.save(member2);
    }
}
