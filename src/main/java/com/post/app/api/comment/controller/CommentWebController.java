package com.post.app.api.comment.controller;

import com.post.app.api.comment.request.CreateCommentRequest;
import com.post.app.api.comment.request.UpdateCommentRequest;
import com.post.app.domain.comment.Comment;
import com.post.app.service.CommentService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CommentWebController {

    private final CommentService commentService;

    @PostMapping("/comments/new")
    public ResponseEntity<Comment> createComment(@RequestBody CreateCommentRequest request) {
        log.info("create comment request = {}, {}, {}, {}", request.getContent(), request.getMemberId(), request.getBoardId(), request.getCreateAt());
        Comment comment = commentService.createComment(request);
        return ResponseEntity.ok(comment);
    }

    @GetMapping("/comments/comment")
    public ResponseEntity<Comment> findComment(@RequestParam Long commentId) {
        Comment findComment = commentService.findCommentById(commentId);
        return ResponseEntity.ok(findComment);
    }

    @GetMapping("/comments")
    public ResponseEntity<List<Comment>> findCommentByBoardId(@RequestParam Long boardId) {
        List<Comment> commentList = commentService.findAllComment(boardId);
        return ResponseEntity.ok(commentList);
    }

    @PostMapping("/comments/update")
    public ResponseEntity<Comment> updateComment(@RequestBody UpdateCommentRequest request) {
        Comment newComment = commentService.updateComment(request.getCommentId(), request.getNewComment());
        return ResponseEntity.ok(newComment);
    }

    @PostMapping("/comments/delete")
    public ResponseEntity<Void> deleteComment(@RequestParam Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok().build();
    }
}
