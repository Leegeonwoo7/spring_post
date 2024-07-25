package com.post.api.board.request;

import com.post.domain.member.Member;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateBoardRequest {

    private String title;
    private String content;
    private Long memberId;
}
