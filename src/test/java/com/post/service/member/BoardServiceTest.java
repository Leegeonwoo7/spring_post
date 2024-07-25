package com.post.service.member;

import com.post.api.board.request.CreateBoardRequest;
import com.post.api.board.request.SearchCond;
import com.post.api.board.request.UpdateBoardRequest;
import com.post.api.board.response.BoardResponse;
import com.post.domain.board.Board;
import com.post.domain.member.Member;
import com.post.repository.member.MemberRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.MethodOrderer.*;

@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
class BoardServiceTest {

    @Autowired
    BoardService boardService;

    @Autowired
    MemberRepository memberRepository;

    @Test
    @Order(1)
    @DisplayName("게시글을 저장하면 저장한 회원을 조회한다")
    @Transactional
    void createBoard() {
        // given
        Member memberA = Member.builder()
                .loginId("memberA")
                .password("1234")
                .name("멤버A")
                .build();
        memberRepository.save(memberA);

        CreateBoardRequest boardRequest = CreateBoardRequest.builder()
                .title("게시글 A")
                .content("안녕하세요")
                .member(memberA)
                .build();

        //when
        BoardResponse boardResponse = boardService.createBoard(boardRequest);

        //then
        assertThat(boardResponse.getMember()).isEqualTo(memberA);
        assertThat(boardResponse.getTitle()).isEqualTo(boardRequest.getTitle());
    }

    @Test
    @Order(2)
    @Transactional
    @DisplayName("저장된 게시글의 ID값으로 게시글을 조회한다")
    void searchBoardById() {
        // given
        CreateBoardRequest boardRequest = CreateBoardRequest.builder()
                .title("게시글 A")
                .content("안녕하세요")
                .build();

        BoardResponse boardResponse = boardService.createBoard(boardRequest);
        Long id = boardResponse.getId();

        //when
        BoardResponse result = boardService.searchBoardById(id);

        //then
        assertThat(result.getId()).isEqualTo(id);
        assertThat(boardResponse.getTitle()).isEqualTo(result.getTitle());
        assertThat(boardResponse.getContent()).isEqualTo(result.getContent());
        assertThat(boardResponse).isEqualTo(result);
    }

    @Test
    @Order(3)
    @Transactional
    @DisplayName("글 제목으로 검색시에 조건에 맞는 게시글만 검색된다 (2건)")
    void searchBoardByCondition() {
        // given
        CreateBoardRequest boardRequestA = CreateBoardRequest.builder()
                .title("게시글 A")
                .content("안녕하세요")
                .build();

        CreateBoardRequest boardRequestB = CreateBoardRequest.builder()
                .title("게B시글")
                .content("안녕하세요2")
                .build();

        CreateBoardRequest boardRequestC = CreateBoardRequest.builder()
                .title("C게시글 ")
                .content("안녕하세요3")
                .build();

        boardService.createBoard(boardRequestA);
        boardService.createBoard(boardRequestB);
        boardService.createBoard(boardRequestC);
        SearchCond cond = new SearchCond("게시글");

        //when
        List<BoardResponse> resultList = boardService.searchBoardByCondition(cond);

        //then
        assertThat(resultList).hasSize(2);
    }

    @Test
    @Order(4)
    @Transactional
    @DisplayName("게시글을 수정하면 수정한 게시글이 반영된다")
    void editBoard() {
        // given
        CreateBoardRequest oldBoard = CreateBoardRequest.builder()
                .title("게시글 A")
                .content("안녕하세요")
                .build();
        BoardResponse saveBoard = boardService.createBoard(oldBoard);
        BoardResponse findBoard = boardService.searchBoardById(saveBoard.getId());
        assertThat(saveBoard).isEqualTo(findBoard);

        UpdateBoardRequest newBoard = UpdateBoardRequest.builder()
                .title("new title")
                .content("new content")
                .build();

        //when
        BoardResponse editBoard = boardService.editBoard(saveBoard.getId(), newBoard);

        //then
        assertThat(editBoard.getContent()).isEqualTo(newBoard.getContent());
        assertThat(editBoard.getTitle()).isEqualTo(newBoard.getTitle());
        assertThat(editBoard.getId()).isEqualTo(saveBoard.getId());
    }

    @Test
    @Order(5)
    @Transactional
    @DisplayName("저장되어있는 모든 게시글을 조회한다 (3건)")
    void findAll() {
        // given
        CreateBoardRequest boardRequestA = CreateBoardRequest.builder()
                .title("게시글 A")
                .content("안녕하세요")
                .build();

        CreateBoardRequest boardRequestB = CreateBoardRequest.builder()
                .title("게B시글")
                .content("안녕하세요2")
                .build();

        CreateBoardRequest boardRequestC = CreateBoardRequest.builder()
                .title("C게시글 ")
                .content("안녕하세요3")
                .build();

        boardService.createBoard(boardRequestA);
        boardService.createBoard(boardRequestB);
        boardService.createBoard(boardRequestC);

        //when
        List<BoardResponse> findAll = boardService.findAllBoard();

        //then
        assertThat(findAll).hasSize(3);
    }
}