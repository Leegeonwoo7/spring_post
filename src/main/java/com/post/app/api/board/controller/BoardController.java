package com.post.app.api.board.controller;

import com.post.app.api.board.request.CreateBoardRequest;
import com.post.app.api.board.request.UpdateBoardRequest;
import com.post.app.api.board.response.BoardResponse;
import com.post.app.common.SearchCond;
import com.post.app.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/boards")
    public ResponseEntity<List<BoardResponse>> getBoards() {
        List<BoardResponse> boardList = boardService.findAllBoard();
        return ResponseEntity.ok(boardList);
    }

    @GetMapping("/boards/{boardId}")
    public ResponseEntity<BoardResponse> getBoard(@PathVariable Long boardId) {
        BoardResponse findBoard = boardService.searchBoardById(boardId);
        return ResponseEntity.ok(findBoard);
    }

    @GetMapping("/boards/search")
    public ResponseEntity<List<BoardResponse>> getBoardsByCondition(
            @RequestBody SearchCond cond
    ) {
        log.info("Condition = {}", cond.getTitle());
        List<BoardResponse> boardList = boardService.searchBoardByCondition(cond);
        return ResponseEntity.ok(boardList);
    }

    @PostMapping("/boards/new")
    public ResponseEntity<BoardResponse> createBoard(@RequestBody CreateBoardRequest request) {
        BoardResponse boardResponse = boardService.createBoard(request);
        return ResponseEntity.ok(boardResponse);
    }

    @PostMapping("/boards/edit")
    public ResponseEntity<BoardResponse> updateBoard(@RequestBody UpdateBoardRequest request) {
        BoardResponse updateBoard = boardService.editBoard(request.getId(), request);
        return ResponseEntity.ok(updateBoard);
    }
}
