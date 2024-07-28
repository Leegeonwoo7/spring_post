package com.post.app.api.like.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class LikeRequest {

    private Long memberId;
    private Long boardId;
    private LocalDateTime createAt;

    @Builder
    private LikeRequest(Long memberId, Long boardId, LocalDateTime createAt) {
        this.memberId = memberId;
        this.boardId = boardId;
        this.createAt = createAt;
    }
}
