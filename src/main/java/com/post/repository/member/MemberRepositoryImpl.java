package com.post.repository.member;

import com.post.domain.member.Member;
import com.post.exception.ExistMemberEmailException;
import com.post.exception.NotFoundMemberException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public boolean isExistEmail(String email) {
        String jpql = "select m from Member m where m.email = :email";
        List<Member> result = em.createQuery(jpql, Member.class)
                .setParameter("email", email)
                .getResultList();

        return !result.isEmpty();
    }

    @Override
    public boolean isExistLoginId(String loginId) {
        String jpql = "select m from Member m where m.loginId = :loginId";
        List<Member> result = em.createQuery(jpql, Member.class)
                .setParameter("loginId", loginId)
                .getResultList();

        return !result.isEmpty();
    }

    @Override
    public boolean isDuplicateName(String name) {
        String jpql = "select m from Member m where m.name = :name or m.loginId = :name";
        List<Member> result = em.createQuery(jpql, Member.class)
                .setParameter("name", name)
                .getResultList();

        return !result.isEmpty();
    }

    @Override
    public void delete(Long id) {
        Member findMember = em.find(Member.class, id);
        em.remove(findMember);
    }
}
