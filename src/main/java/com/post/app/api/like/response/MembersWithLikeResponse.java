package com.post.app.api.like.response;

import com.post.app.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

/**
 * 게시글에 좋아요를 누른 회원이름 조회
 */
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
