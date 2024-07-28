package com.post.app.repository.board;

import com.post.app.api.board.request.SearchCond;
import com.post.app.domain.board.Board;

import java.util.List;

public interface BoardRepository {

    Board save(Board board);

    List<Board> findByCondition(SearchCond condition);

    Board findById(Long id);

    List<Board> findAll();

    void delete(Long id);

    Board update(Long id, Board board);


}
