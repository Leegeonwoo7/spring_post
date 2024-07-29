package com.post.app.api.comment.request;

import lombok.Getter;

@Getter
public class UpdateCommentRequest {

    private Long commentId;
    private String newComment;
}
