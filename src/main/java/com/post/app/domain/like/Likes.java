package com.post.domain.like;

import com.post.domain.board.Board;
import com.post.domain.member.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Likes {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;

    private LocalDateTime createAt;

    @Builder
    private Likes(Member member, Board board, LocalDateTime createAt) {
        this.member = member;
        this.board = board;
        this.createAt = createAt;
    }
}
