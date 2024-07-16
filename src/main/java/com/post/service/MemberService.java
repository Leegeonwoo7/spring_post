package com.post.service;

import com.post.domain.member.Member;
import com.post.exception.ExistMemberEmailException;
import com.post.exception.ExistMemberLoginIdException;
import com.post.exception.ParameterException;
import com.post.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public Member join(Member member) {
        if (member == null) {
            throw new ParameterException("회원가입하고자하는 회원 데이터는 NULL이어서는 안됩니다.");
        }

        validateParamMember(member);

        log.info("[회원가입 정보] loginId: {}", member);
        return memberRepository.save(member);
    }

    private void validateParamMember(Member member) {
        boolean existEmail = memberRepository.isExistEmail(member.getEmail());
        if (existEmail) {
            throw new ExistMemberEmailException("이미 존재하는 이메일로 회원가입을 요청하였습니다.");
        }
        boolean existLoginId = memberRepository.isExistLoginId(member.getLoginId());
        if (existLoginId) {
            throw new ExistMemberLoginIdException("이미 존재하는 로그인아이디로 회원가입을 요청하였습니다.");
        }
    }
}
