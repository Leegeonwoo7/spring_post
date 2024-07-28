package com.post.repository.likes;

import com.post.domain.like.Likes;
import com.post.domain.member.Member;

import java.util.List;

public interface LikeRepository {

    /**
     * 1. 해당 게시글에 대해 좋아요를 누른 회원 조회
     * 2. 해당 게시글의 좋아요 수 반환
     * 3. 멤버와 게시글 id를 각각받아 좋아요 반영한다
     */

    List<Member> findMembersByLike(Long boardId);
    Long findLikeCount(Long boardId);
    Likes save(Likes likes);
}
