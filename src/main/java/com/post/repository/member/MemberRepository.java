package com.post.repository.member;

import com.post.domain.member.Member;

/**
 * 1. 저장
 * 2. 조회
 *  - id로 조회
 *  - name 또는 login_id 으로 조회
 * 3. 수정
 *  - 비밀번호 변경
 *  - 회원정보 변경
 *  - name 변경
 * 4. 삭제(탈퇴)
 */
public interface MemberRepository {

    Member save(Member member);

    Member findById(Long id);
    Member findByName(String name);

    Member updatePassword(Long id, String newPassword);
    Member updateInfo(Long id, Member member);
    Member updateName(Long id, String newName);

    void delete(Long id);

}
