package com.post.api.board.response;

import com.post.domain.board.Board;
import com.post.domain.member.Member;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class BoardResponse {

    private Long id;
    private String title;
    private String content;
    private Member member;

    public static BoardResponse from(Board board) {
        return new BoardResponse(
                board.getId(),
                board.getTitle(),
                board.getContent(),
                board.getMember()
        );
    }
}
