package com.post.api.member.controller;

import com.post.api.member.request.ChangePasswordRequest;
import com.post.api.member.request.MemberCreateRequest;
import com.post.api.member.response.MemberResponse;
import com.post.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members/add")
    public ResponseEntity<MemberResponse> createAccount(@RequestBody MemberCreateRequest request) {
        MemberResponse memberResponse = memberService.join(request);
        return ResponseEntity.ok(memberResponse);
    }

    @GetMapping("/members")
    public ResponseEntity<List<MemberResponse>> findMembers() {
        List<MemberResponse> memberResponse = memberService.findMemberList();
        return ResponseEntity.ok(memberResponse);
    }

    @GetMapping("/members/{memberId}")
    public ResponseEntity<MemberResponse> findMember(@PathVariable Long memberId) {
        MemberResponse memberResponse = memberService.findMemberById(memberId);
        return ResponseEntity.ok(memberResponse);
    }

    @PostMapping("/members/edit/{memberId}")
    public ResponseEntity<MemberResponse> editMember(@PathVariable Long memberId, @RequestBody ChangePasswordRequest request) {
        MemberResponse memberResponse = memberService.changePassword(memberId, request.getPassword());
        return ResponseEntity.ok(memberResponse);
    }

    @PostMapping("/members/delete/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long memberId) {
        memberService.deleteMember(memberId);
        return ResponseEntity.ok().build();
    }
}
