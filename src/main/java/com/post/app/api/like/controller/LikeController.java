package com.post.app.api.like.controller;

import com.post.app.api.like.request.LikeRequest;
import com.post.app.api.like.response.MembersWithLikeResponse;
import com.post.app.domain.like.Likes;
import com.post.app.service.LikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/likes/new")
    public ResponseEntity<Likes> addLike(@RequestBody LikeRequest request) {
        log.info("memberId = {}", request.getMemberId());
        log.info("boardId = {}", request.getBoardId());
        Likes likes = likeService.addLike(request);
        return ResponseEntity.ok(likes);
    }

    @GetMapping("/likes/count/{boardId}")
    public ResponseEntity<Long> checkCountLikes(@PathVariable Long boardId) {
        Long resultCount = likeService.countLikesByBoardId(boardId);
        return ResponseEntity.ok(resultCount);
    }

    @GetMapping("/likes/detail/{boardId}")
    public ResponseEntity<List<MembersWithLikeResponse>> checkMembersLikes(@PathVariable Long boardId) {
        List<MembersWithLikeResponse> resultList = likeService.findMembersByLikeBoard(boardId);
        return ResponseEntity.ok(resultList);
    }
}
