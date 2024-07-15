package com.post.domain.board;

import com.post.domain.member.Member;
import jakarta.persistence.*;
import lombok.Getter;

import static jakarta.persistence.GenerationType.*;

@Entity
@Getter
public class Board {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "board_id")
    private Long id;

//    @Column(length = 100, nullable = false)
    private String title;

    @Lob
//    @Column(nullable = false)
    private String content;

    @ManyToOne
    private Member member;
}
