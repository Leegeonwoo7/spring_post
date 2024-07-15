package com.post.domain.like;

import com.post.domain.board.Board;
import com.post.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDateTime;

@Entity
@Getter
public class Like {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_id")
    private Long id;

    @ManyToOne
    private Member member;

    @ManyToOne
    private Board board;

    private LocalDateTime createAt;
}
