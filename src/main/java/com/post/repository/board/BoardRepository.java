package com.post.repository.board;

import com.post.api.board.request.SearchCond;
import com.post.domain.board.Board;

import java.util.List;

public interface BoardRepository {

    Board save(Board board);

    List<Board> findByCondition(SearchCond condition);

    Board findById(Long id);

    void delete(Long id);

    Board update(Long id, Board board);
}
