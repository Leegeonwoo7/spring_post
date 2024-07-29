package com.post.app.domain.comment;

import com.post.app.domain.board.Board;
import com.post.app.domain.member.Member;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    private LocalDateTime createAt;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    private Comment(String content, LocalDateTime createAt, Member member, Board board) {
        this.content = content;
        this.createAt = createAt;
        this.member = member;
        this.board = board;
    }

    public Comment changeComment(String newContent) {
        this.content = newContent;
        return this;
    }
}
