package com.post.app.common;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SearchCond {

    private String title;

    @Builder
    public SearchCond(String title) {
        this.title = title;
    }
}
