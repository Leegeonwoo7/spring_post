package com.post.repository.board;

import com.post.api.board.request.SearchCond;
import com.post.domain.board.Board;
import com.post.domain.member.Member;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class BoardRepositoryImplTest {

    @Autowired
    BoardRepository boardRepository;

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

}