package com.post.repository.board;

import com.post.api.board.request.SearchCond;
import com.post.domain.board.Board;
import com.post.domain.member.Member;
import com.post.exception.board.NotFoundBoardException;
import com.post.repository.member.MemberRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.transaction.annotation.Transactional;

import javax.lang.model.UnknownEntityException;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BoardRepositoryImplTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    private Member memberA;
    private Member memberB;
    private Board boardA;
    private Board boardB;

    @BeforeEach
    void setUp() {
        memberA = Member.builder()
                .loginId("memberA")
                .password("1234")
                .name("멤버A")
                .build();
        memberB = Member.builder()
                .loginId("memberB")
                .password("4567")
                .name("멤버B")
                .build();

        boardA = Board.builder()
                .title("게시글 A")
                .content("게시글 A입니다.")
                .member(memberA)
                .build();

        boardB = Board.builder()
                .title("게시글 B")
                .content("게시글 B입니다.")
                .member(memberB)
                .build();
    }

    @Test
    @Order(1)
    @DisplayName("게시글을 저장하면 데이터베이스에서 조회할 수 있다")
    void saveBoard() {
        // given

        //when
        Board saveBoard = boardRepository.save(boardA);

        //then
        assertThat(saveBoard).isEqualTo(boardA);
    }
    
    @Test
    @Order(2)
    @DisplayName("저장된 게시글을 조회할 수 있다")
    void findById() {
        // given
        Board saveBoard = boardRepository.save(boardA);

        //when
        Board findBoard = boardRepository.findById(saveBoard.getId());

        //then
        assertThat(saveBoard).isEqualTo(findBoard);
        assertThat(saveBoard.getMember()).isEqualTo(findBoard.getMember());
        assertThat(saveBoard.getTitle()).isEqualTo(findBoard.getTitle());
    }

    @Test
    @Order(3)
    @DisplayName("게시글 제목으로 글 단건 조회")
    void findByCondition() {
        //given
        boardRepository.save(boardA);

        SearchCond cond = SearchCond.builder()
                .title("A")
                .build();

        //when
        List<Board> boardList = boardRepository.findByCondition(cond);

        //then
        assertThat(boardList).hasSize(1);
    }

    @Test
    @Order(4)
    @DisplayName("게시글 제목으로 조회할 때 여러건을 조회할 수 있어야한다")
    void findByConditionMultiple() {
        // given
        Board boardC = Board.builder()
                .member(memberA)
                .content("이 글은 게시글 C입니다.")
                .title("C 게시글 입니다.")
                .build();

        Board boardD = Board.builder()
                .member(memberA)
                .content("이 글은 게시글 D입니다.")
                .title("게시글D")
                .build();

        boardRepository.save(boardA);
        boardRepository.save(boardB);
        boardRepository.save(boardC);
        boardRepository.save(boardD);

        SearchCond cond = SearchCond.builder()
                .title("게시글")
                .build();
        //when
        List<Board> boardList = boardRepository.findByCondition(cond);

        //then
        assertThat(boardList).hasSize(4);
        boardList.forEach(board -> assertThat(board.getTitle()).contains("게시글"));
    }

    @Test
    @Order(5)
    @DisplayName("게시글을 변경하면 업데이트된 게시글이 반영된다")
    void updateBoard() {
        // given
        Board saveBoardA = boardRepository.save(boardA);
        Board saveBoardB = boardRepository.save(boardB);

        Board newBoardA = Board.builder()
                .title("새로운 게시글 A")
                .content("새로운 게시글의 내용 A")
                .build();

        Board newBoardB = Board.builder()
                .title("새로운 게시글 B")
                .content("새로운 게시글의 내용 B")
                .build();

        //when
        Board updateBoardA = boardRepository.update(saveBoardA.getId(), newBoardA);
        Board updateBoardB = boardRepository.update(saveBoardB.getId(), newBoardB);

        Board findBoardA = boardRepository.findById(updateBoardA.getId());
        Board findBoardB = boardRepository.findById(updateBoardB.getId());

        //then
        assertThat(newBoardA.getTitle()).isEqualTo(findBoardA.getTitle());
        assertThat(newBoardB.getTitle()).isEqualTo(findBoardB.getTitle());
        assertThat(newBoardA.getContent()).isEqualTo(findBoardA.getContent());
        assertThat(newBoardB.getContent()).isEqualTo(findBoardB.getContent());
    }

    @Test
    @Order(6)
    @DisplayName("게시글을 삭제하면 해당 게시글은 id로 조회되어서는 안된다")
    void delete() {
        //given
        Board saveBoard = boardRepository.save(boardA);

        //when
        boardRepository.delete(saveBoard.getId());

        //then
        assertThatThrownBy(() -> boardRepository.findById(saveBoard.getId()))
                .isInstanceOf(NotFoundBoardException.class);
    }


}