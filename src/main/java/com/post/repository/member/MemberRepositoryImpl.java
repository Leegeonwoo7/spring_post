package com.post.repository.member;

import com.post.domain.member.Member;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
public class MemberRepositoryImpl implements MemberRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Member save(Member member) {
        em.persist(member);
        return member;
    }

    @Override
    public Member findById(Long id) {
        return em.find(Member.class, id);
    }

    @Override
    public Member findByName(String name) {
        String jpql = "select m from Member m where m.name = :name";
        return em.createQuery(jpql, Member.class)
                .setParameter("name", name)
                .getSingleResult();
    }

    @Override
    public Member updatePassword(Long id, String newPassword) {
        return null;
    }

    @Override
    public Member updateInfo(Long id, Member member) {
        return null;
    }

    @Override
    public Member updateName(Long id, String newName) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
