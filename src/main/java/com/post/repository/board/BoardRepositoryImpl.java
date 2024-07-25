package com.post.repository.board;

import com.post.api.board.request.SearchCond;
import com.post.domain.board.Board;
import com.post.domain.member.Member;
import com.post.exception.board.NotFoundBoardException;
import com.post.repository.mybatis.BoardMapper;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepositoryImpl implements BoardRepository{

    private final EntityManager em;
    private final BoardMapper boardMapper;

    @Override
    public Board save(Board board) {
        em.persist(board);
        return board;
    }

    @Override
    public Board findById(Long id) {
        Board findBoard = em.find(Board.class, id);
        if (findBoard == null) {
            throw new NotFoundBoardException("해당 아이디의 게시글을 찾을 수 없습니다.");
        }
        return findBoard;
    }

    @Override
    public void delete(Long id) {
        Board board = em.find(Board.class, id);
        em.remove(board);
    }

    @Override
    public Board update(Long id, Board board) {
        Board findBoard = findById(id);
        return findBoard.changeBoard(board);
    }

    @Override
    public List<Board> findByCondition(SearchCond condition) {
        return boardMapper.findByCondition(condition);
    }
}
