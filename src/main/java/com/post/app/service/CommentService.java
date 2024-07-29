package com.post.app.service;

import com.post.app.api.board.response.BoardResponse;
import com.post.app.api.comment.request.CreateCommentRequest;
import com.post.app.api.member.response.MemberResponse;
import com.post.app.domain.board.Board;
import com.post.app.domain.comment.Comment;
import com.post.app.domain.member.Member;
import com.post.app.repository.board.BoardRepository;
import com.post.app.repository.comment.CommentRepository;
import com.post.app.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final MemberService memberService;
    private final BoardService boardService;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public Comment createComment(CreateCommentRequest request) {
        log.info("CommentService memberId: {}",request.getMemberId());
        log.info("CommentService boardId: {}",request.getBoardId());
        MemberResponse memberResponse = memberService.findMemberById(request.getMemberId());
//        Member memberResponse = memberRepository.findById(request.getMemberId());
        log.info("CommentService memberResponse: {}", memberResponse.getLoginId());
        BoardResponse boardResponse = boardService.searchBoardById(request.getBoardId());
        log.info("CommentService boardResponse.getMember: {}", boardResponse.getMember().getName());
//        Board boardResponse = boardRepository.findById(request.getBoardId());

        Comment comment = Comment.builder()
                .content(request.getContent())
                .member(memberResponse.toEntity())
                .board(boardResponse.toEntity(boardResponse.getMember()))
                .createAt(LocalDateTime.now())
                .build();
        log.info("CommentService comment.builder: {}, {}, {}, {}, {}", comment.getId(), comment.getMember().getName(), comment.getBoard().getTitle(), comment.getCreateAt(), comment.getContent());
        return commentRepository.save(comment);
    }

    @Transactional(readOnly = true)
    public List<Comment> findAllComment(Long boardId) {
        return commentRepository.findAllByBoardId(boardId);
    }

    @Transactional(readOnly = true)
    public Comment findCommentById(Long id) {
        return commentRepository.findById(id);
    }

    @Transactional
    public Comment updateComment(Long commentId, String newComment) {
        Comment findComment = findCommentById(commentId);
        return commentRepository.update(findComment.getId(), newComment);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        deleteComment(commentId);
    }
}
