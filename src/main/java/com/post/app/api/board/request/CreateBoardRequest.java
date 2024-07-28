package com.post.app.api.board.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateBoardRequest {

    private String title;
    private String content;
    private Long memberId;
}
