package com.post.api.like.response;

import com.post.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MembersWithLikeResponse {

    private Long memberId;
    private String name;

    @Builder
    private MembersWithLikeResponse(Long memberId, String name) {
        this.memberId = memberId;
        this.name = name;
    }

    public static MembersWithLikeResponse from(Member member) {
        return new MembersWithLikeResponse(
                member.getId(),
                member.getLoginId()
        );
    }
}
