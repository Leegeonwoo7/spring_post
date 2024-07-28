package com.post.service.like;

import com.post.api.like.request.LikeRequest;
import com.post.domain.board.Board;
import com.post.domain.like.Likes;
import com.post.domain.member.Member;
import com.post.repository.board.BoardRepository;
import com.post.repository.member.MemberRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class LikeServiceTest {

    @Autowired LikeService likeService;
    @Autowired MemberRepository memberRepository;
    @Autowired BoardRepository boardRepository;

    @Test
    @Order(1)
    @DisplayName("게시글ID와 회원ID에 맞는 좋아요를 반영한다")
    void addLike() {
        // given
        Member memberA = Member.builder()
                .loginId("memberA")
                .password("1234")
                .build();
        Member saveMember = memberRepository.save(memberA);

        Board board = Board.builder()
                .member(memberA)
                .title("게시글")
                .content("test")
                .build();
        Board saveBoard = boardRepository.save(board);

        LikeRequest request = LikeRequest.builder()
                .memberId(saveMember.getId())
                .boardId(saveBoard.getId())
                .build();

        //when
        Likes likes = likeService.addLike(request);

        //then
        assertThat(likes.getId()).isEqualTo(1L);
        assertThat(likes.getBoard().getTitle()).isEqualTo("게시글");
        assertThat(likes.getMember().getLoginId()).isEqualTo("memberA");
    }

    @Test
    @Order(2)
    @DisplayName("게시글에 좋아요를 누른 회원의 수 조회")
    void countLikesByBoardId() {
        // given
        Member ownerMember = Member.builder()
                .loginId("owner")
                .password("1234")
                .build();
        Member memberA = Member.builder()
                .loginId("memberA")
                .password("1234")
                .build();
        Member memberB = Member.builder()
                .loginId("memberB")
                .password("1234")
                .build();
        Member memberC = Member.builder()
                .loginId("memberC")
                .password("1234")
                .build();
        memberRepository.save(ownerMember);
        memberRepository.save(memberA);
        memberRepository.save(memberB);
        memberRepository.save(memberC);

        Board board = Board.builder()
                .title("게시글 A")
                .content("게시글입니다.")
                .member(ownerMember)
                .build();
        Board saveBoard = boardRepository.save(board);

        LikeRequest like1 = LikeRequest.builder()
                .boardId(saveBoard.getId())
                .memberId(memberA.getId())
                .build();

        LikeRequest like2 = LikeRequest.builder()
                .boardId(saveBoard.getId())
                .memberId(memberB.getId())
                .build();
        LikeRequest like3 = LikeRequest.builder()
                .boardId(saveBoard.getId())
                .memberId(memberC.getId())
                .build();
        likeService.addLike(like1);
        likeService.addLike(like2);
        likeService.addLike(like3);

        //when
        Long resultCount = likeService.countLikesByBoardId(saveBoard.getId());

        //then
        assertThat(resultCount).isEqualTo(3L);
    }
}