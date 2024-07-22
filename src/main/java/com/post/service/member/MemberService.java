package com.post.service.member;

import com.post.api.member.request.MemberCreateRequest;
import com.post.api.member.response.MemberResponse;
import com.post.domain.member.Member;
import com.post.exception.ExistMemberEmailException;
import com.post.exception.ExistMemberLoginIdException;
import com.post.exception.ExistMemberNameException;
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

    public MemberResponse join(MemberCreateRequest requestMember) {
        if (requestMember == null) {
            throw new ParameterException("회원가입하고자하는 회원 데이터는 NULL이어서는 안됩니다.");
        }

        validateParamMember(requestMember);
        Member member = Member.builder()
                .loginId(requestMember.getLoginId())
                .password(requestMember.getPassword())
                .name(requestMember.getName())
                .email(requestMember.getEmail())
                .phone(requestMember.getPhone())
                .address(requestMember.getAddress())
                .birthdate(requestMember.getBirthdate())
                .createAt(requestMember.getCreateAt())
                .role(requestMember.getRole())
                .build();

        if (member.getName() == null) {
            member.changeName(member.getLoginId());
        }
        memberRepository.save(member);

        log.info("[회원가입 정보] loginId: {}", member);
        return MemberResponse.from(member);
    }

    @Transactional(readOnly = true)
    public MemberResponse findMemberById(Long id) {
        Member findMember = memberRepository.findById(id);

        return MemberResponse.from(findMember);
    }

    public MemberResponse changePassword(Long id, String newPassword) {
        Member findMember = memberRepository.findById(id);

        findMember.changePassword(newPassword);
        return MemberResponse.from(findMember);
    }

    public MemberResponse changeName(Long id, String newName) {
        Member findMember = memberRepository.findById(id);

        findMember.changeName(newName);
        return MemberResponse.from(findMember);
    }

    private void validateParamMember(MemberCreateRequest member) {
        boolean existEmail = memberRepository.isExistEmail(member.getEmail());
        if (existEmail) {
            throw new ExistMemberEmailException("이미 존재하는 이메일로 회원가입을 요청하였습니다.");
        }
        boolean existLoginId = memberRepository.isExistLoginId(member.getLoginId());
        if (existLoginId) {
            throw new ExistMemberLoginIdException("이미 존재하는 로그인아이디로 회원가입을 요청하였습니다.");
        }
        boolean duplicateName = memberRepository.isDuplicateName(member.getName());
        if (duplicateName) {
            throw new ExistMemberNameException("이미 존재하는 이름으로 회원가입을 요청하였습니다.");
        }
    }

}
