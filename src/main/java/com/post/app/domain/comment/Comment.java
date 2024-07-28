package com.post.app.domain.comment;

import com.post.app.domain.board.Board;
import com.post.app.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Comment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @Lob
    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;
    private LocalDateTime createAt;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Board board;
}
