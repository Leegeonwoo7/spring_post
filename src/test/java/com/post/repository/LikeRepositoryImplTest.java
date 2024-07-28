package com.post.repository.likes;

import com.post.app.repository.likes.LikeRepository;
import com.post.app.domain.board.Board;
import com.post.app.domain.like.Likes;
import com.post.app.domain.member.Member;
import com.post.app.repository.board.BoardRepository;
import com.post.app.repository.member.MemberRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class LikeRepositoryImplTest {

    @Autowired
    LikeRepository likeRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired BoardRepository boardRepository;
    @Autowired EntityManager em;

    @Test
    @DisplayName("좋아요를 누르면 DB에 반영되고 해당 좋아요가 반환된다")
    void saveLike() {
        // given
        Member member = Member.builder()
                .loginId("memberA")
                .password("1234")
                .build();
        memberRepository.save(member);

        Board board = Board.builder()
                .title("안녕하세요")
                .content("안녕하세요 인사드립니다.")
                .member(member)
                .build();
        boardRepository.save(board);

        Likes like = Likes.builder()
                .member(member)
                .board(board)
                .build();

        //when
        Likes saveLike = likeRepository.save(like);

        //then
        assertThat(saveLike.getBoard().getId()).isEqualTo(board.getId());
        assertThat(saveLike.getMember().getId()).isEqualTo(member.getId());
    }

    @Test
    @DisplayName("해당 게시글에 좋아요를 누른 회원목록을 조회한다")
    void findMemberByLike() {
        // given
        Member boardOwner = Member.builder()
                .loginId("boardOwner")
                .password("1234")
                .build();
        memberRepository.save(boardOwner);

        Member memberB = Member.builder()
                .loginId("memberB")
                .password("12345")
                .build();
        memberRepository.save(memberB);

        Member memberC = Member.builder()
                .loginId("memberC")
                .password("12347")
                .build();
        memberRepository.save(memberC);

        Board board = Board.builder()
                .title("안녕하세요")
                .content("안녕하세요 인사드립니다.")
                .member(boardOwner)
                .build();
        Board saveBoard = boardRepository.save(board);

        Likes like1 = Likes.builder()
                .member(memberB)
                .board(board)
                .build();

        Likes like2 = Likes.builder()
                .member(memberC)
                .board(board)
                .build();
        likeRepository.save(like1);
        likeRepository.save(like2);

        em.flush();
        em.clear();

        //when
        List<Member> resultList = likeRepository.findMembersByLike(saveBoard.getId());

        //then
        assertThat(resultList).hasSize(2);
        resultList.forEach(member -> assertThat(member.getId()).isNotNull());
    }
    
    @Test
    @DisplayName("해당 게시글에 등록된 좋아요의 수를 반환한다")
    void findLikeCount() {
        // given
        Member boardOwner = Member.builder()
                .loginId("boardOwner")
                .password("1234")
                .build();
        memberRepository.save(boardOwner);

        Member memberB = Member.builder()
                .loginId("memberB")
                .password("12345")
                .build();
        memberRepository.save(memberB);

        Member memberC = Member.builder()
                .loginId("memberC")
                .password("12347")
                .build();
        memberRepository.save(memberC);

        Board board = Board.builder()
                .title("안녕하세요")
                .content("안녕하세요 인사드립니다.")
                .member(boardOwner)
                .build();
        Board saveBoard = boardRepository.save(board);

        Likes like1 = Likes.builder()
                .member(memberB)
                .board(board)
                .build();

        Likes like2 = Likes.builder()
                .member(memberC)
                .board(board)
                .build();
        likeRepository.save(like1);
        likeRepository.save(like2);

        em.flush();
        em.clear();
        
        //when
        Long likeCount = likeRepository.findLikeCount(saveBoard.getId());

        //then
        assertThat(likeCount).isEqualTo(2);
    }
}