package com.post.app.repository.likes;

import com.post.app.domain.like.Likes;
import com.post.app.domain.member.Member;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class LikeRepositoryImpl implements LikeRepository{

    private final EntityManager em;

    @Override
    public List<Member> findMembersByLike(Long boardId) {
        String jpql = "select l.member from Likes l where l.board.id = :boardId";
        return em.createQuery(jpql, Member.class)
                .setParameter("boardId", boardId)
                .getResultList();
    }

    @Override
    public Long findLikeCount(Long boardId) {
        String jpql = "select count(l) from Likes l where l.board.id = :boardId";
        return em.createQuery(jpql, Long.class)
                .setParameter("boardId", boardId)
                .getSingleResult();
    }

    @Override
    public Likes save(Likes likes) {
        em.persist(likes);
        return likes;
    }


}