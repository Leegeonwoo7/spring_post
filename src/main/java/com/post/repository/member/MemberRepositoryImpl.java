package com.post.repository.member;

import com.post.domain.member.Member;
import com.post.exception.NotFoundMemberException;
import com.post.exception.ParameterException;
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
        Member findMember = em.find(Member.class, id);
        if (findMember == null) {
            throw new NotFoundMemberException("찾고자하는 id의 회원이 존재하지 않습니다.");
        }

        return findMember;
    }

    @Override
    public Member findByName(String name) {
        String jpql = "select m from Member m where m.name = :name";
        Member findMember = em.createQuery(jpql, Member.class)
                .setParameter("name", name)
                .getSingleResult();

        if (findMember == null) {
            throw new NotFoundMemberException("찾고자하는 이름의 회원이 존재하지 않습니다.");
        }
        return findMember;
    }

    @Override
    public void delete(Long id) {

    }
}
