package com.post.app.repository.comment;

import com.post.app.domain.comment.Comment;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class CommentRepositoryImpl implements CommentRepository{

    private final EntityManager em;

    @Override
    public Comment save(Comment comment) {
        em.persist(comment);
        return comment;
    }

    @Override
    public Comment findById(Long id) {
        return em.find(Comment.class, id);
    }

    @Override
    public List<Comment> findAllByBoardId(Long boardId) {
        String jpql = "select c from Comment c where board.id = :boardId";
        return em.createQuery(jpql, Comment.class)
                .setParameter("boardId", boardId)
                .getResultList();
    }

    @Override
    public Comment update(Long commentId, String newComment) {
        Comment findComment = findById(commentId);
        return findComment.changeComment(newComment);
    }

    @Override
    public void delete(Long commentId) {
        em.remove(commentId);
    }
}
