package com.post.api.board.request;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SearchCond {

    private String title;

    @Builder
    private SearchCond(String title) {
        this.title = title;
    }
}
