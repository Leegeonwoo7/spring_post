package com.post.app.service;

import com.post.app.api.board.request.CreateBoardRequest;
import com.post.app.api.board.request.UpdateBoardRequest;
import com.post.app.api.board.response.BoardResponse;
import com.post.app.common.SearchCond;
import com.post.app.domain.board.Board;
import com.post.app.domain.member.Member;
import com.post.app.repository.board.BoardRepository;
import com.post.app.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public BoardResponse createBoard(CreateBoardRequest boardRequest) {
        Member findMember = memberRepository.findById(boardRequest.getMemberId());

        Board createBoard = Board.builder()
                .title(boardRequest.getTitle())
                .content(boardRequest.getContent())
                .member(findMember)
                .build();

        Board saveBoard = boardRepository.save(createBoard);

        return BoardResponse.from(saveBoard);
    }

    @Transactional(readOnly = true)
    public BoardResponse searchBoardById(Long id) {
        Board findBoard = boardRepository.findById(id);
        return BoardResponse.from(findBoard);
    }

    @Transactional(readOnly = true)
    public List<BoardResponse> searchBoardByCondition(SearchCond cond) {
        List<Board> boardList = boardRepository.findByCondition(cond);
        return boardList.stream()
                .map(BoardResponse::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public BoardResponse editBoard(Long id, UpdateBoardRequest boardRequest) {
        Board newBoard = Board.builder()
                .title(boardRequest.getTitle())
                .content(boardRequest.getContent())
                .build();

        Board updateBoard = boardRepository.update(id, newBoard);

        return BoardResponse.from(updateBoard);
    }

    @Transactional(readOnly = true)
    public List<BoardResponse> findAllBoard() {
        List<Board> boardList = boardRepository.findAll();
        return boardList.stream()
                .map(BoardResponse::from)
                .collect(Collectors.toList());
    }
}
