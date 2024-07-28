package com.post.app.repository.likes;

import com.post.app.domain.like.Likes;
import com.post.app.domain.member.Member;

import java.util.List;

public interface LikeRepository {

    List<Member> findMembersByLike(Long boardId);
    Long findLikeCount(Long boardId);
    Likes save(Likes likes);
}
