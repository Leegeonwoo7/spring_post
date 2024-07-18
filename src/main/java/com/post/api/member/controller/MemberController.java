package com.post.api.member.controller;

import com.post.api.member.request.MemberCreateRequest;
import com.post.api.member.response.MemberResponse;
import com.post.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/members/add")
    public ResponseEntity<MemberResponse> createAccount(@RequestBody MemberCreateRequest request) {
        MemberResponse response = memberService.join(request);
        return ResponseEntity.ok(response);
    }
}
