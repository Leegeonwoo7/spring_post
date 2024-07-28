package com.post.app.api.like.response;

import com.post.app.domain.like.Likes;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class LikeResponse {

    private Long likeId;
    private Long memberId;
    private Long boardId;

    @Builder
    private LikeResponse(Long likeId, Long memberId, Long boardId) {
        this.likeId = likeId;
        this.memberId = memberId;
        this.boardId = boardId;
    }

    public LikeResponse from(Likes likes) {
        return new LikeResponse(
                likes.getId(),
                likes.getMember().getId(),
                likes.getBoard().getId()
        );
    }
}
