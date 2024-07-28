package com.post.config;

import com.post.domain.board.Board;
import com.post.domain.member.Address;
import com.post.domain.member.Member;
import com.post.domain.member.Role;
import com.post.repository.board.BoardRepository;
import com.post.repository.board.BoardRepositoryImpl;
import com.post.repository.member.MemberRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Profile("!test")
public class DataInitializer {

    private final MemberRepositoryImpl memberRepository;
    private final BoardRepositoryImpl boardRepository;

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

        Board board1 = Board.builder()
                .title("First Post")
                .content("This is the first post.")
                .member(member1)
                .build();

        Board board2 = Board.builder()
                .title("Second Post")
                .content("This is the second post.")
                .member(member1)
                .build();

        Board board3 = Board.builder()
                .title("Third Post")
                .content("This is the third post.")
                .member(member2)
                .build();

        Board board4 = Board.builder()
                .title("Fourth Post")
                .content("This is the fourth post.")
                .member(member2)
                .build();

        boardRepository.save(board1);
        boardRepository.save(board2);
        boardRepository.save(board3);
        boardRepository.save(board4);
    }
}
