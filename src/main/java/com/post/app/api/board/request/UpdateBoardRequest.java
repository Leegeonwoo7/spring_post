package com.post.app.api.board.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateBoardRequest {

    private Long id;
    private String title;
    private String content;
}
