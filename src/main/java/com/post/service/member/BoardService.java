package com.post.service.member;

import com.post.api.board.request.CreateBoardRequest;
import com.post.api.board.request.SearchCond;
import com.post.api.board.request.UpdateBoardRequest;
import com.post.api.board.response.BoardResponse;
import com.post.domain.board.Board;
import com.post.repository.board.BoardRepository;
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

    @Transactional
    public BoardResponse createBoard(CreateBoardRequest boardRequest) {
        Board createBoard = Board.builder()
                .title(boardRequest.getTitle())
                .content(boardRequest.getContent())
                .member(boardRequest.getMember())
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
