package com.post.api.board.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateBoardRequest {

    private String title;
    private String content;
}
