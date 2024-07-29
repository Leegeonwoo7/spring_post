package com.post.app.api.comment.request;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateCommentRequest {

    private String content;
    private Long boardId;
    private Long memberId;
    private LocalDateTime createAt;
}
