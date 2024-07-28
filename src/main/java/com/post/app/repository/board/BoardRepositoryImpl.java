package com.post.app.repository.board;

import com.post.app.common.SearchCond;
import com.post.app.domain.board.Board;
import com.post.app.exception.board.NotFoundBoardException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Slf4j
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
    public List<Board> findAll() {
        String jpql = "select b from Board b";
        return em.createQuery(jpql, Board.class)
                .getResultList();
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
        List<Board> boardList = boardMapper.findByCondition(condition);
        for (Board board : boardList) {
            log.info("Condition Search - title: {}, id: {}, member: {}", board.getTitle(), board.getId(), board.getMember());
        }
        return boardList;
    }
}
