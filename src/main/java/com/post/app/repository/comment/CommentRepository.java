package com.post.app.repository.comment;

import com.post.app.domain.comment.Comment;

import java.util.List;

public interface CommentRepository {

    Comment save(Comment comment);
    Comment findById(Long id);
    List<Comment> findAllByBoardId(Long boardId);
    Comment update(Long commentId, String newComment);
    void delete(Long commentId);
}
