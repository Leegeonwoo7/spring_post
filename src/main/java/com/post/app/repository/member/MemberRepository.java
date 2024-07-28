package com.post.app.repository.member;

import com.post.app.domain.member.Member;

import java.util.List;

public interface MemberRepository {

    Member save(Member member);

    Member findById(Long id);
    Member findByName(String name);
    List<Member> findMemberList();

    boolean isExistEmail(String email);

    boolean isExistLoginId(String loginId);

    boolean isDuplicateName(String name);

    void delete(Long id);

}
