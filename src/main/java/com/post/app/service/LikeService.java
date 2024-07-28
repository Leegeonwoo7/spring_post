package com.post.app.service;

import com.post.app.api.like.request.LikeRequest;
import com.post.app.api.like.response.MembersWithLikeResponse;
import com.post.app.domain.board.Board;
import com.post.app.domain.like.Likes;
import com.post.app.domain.member.Member;
import com.post.app.repository.board.BoardRepository;
import com.post.app.repository.likes.LikeRepository;
import com.post.app.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public Likes addLike(LikeRequest request) {
        Member findMember = memberRepository.findById(request.getMemberId());
        Board findBoard = boardRepository.findById(request.getBoardId());

        Likes like = Likes.builder()
                .member(findMember)
                .board(findBoard)
                .createAt(LocalDateTime.now())
                .build();

        return likeRepository.save(like);
    }

    @Transactional(readOnly = true)
    public Long countLikesByBoardId(Long boardId) {
        return likeRepository.findLikeCount(boardId);
    }

    @Transactional
    public List<MembersWithLikeResponse> findMembersByLikeBoard(Long boardId) {
        List<Member> findMembers = likeRepository.findMembersByLike(boardId);
        List<MembersWithLikeResponse> responseList = new ArrayList<>();

        for (Member findMember : findMembers) {
            MembersWithLikeResponse from = MembersWithLikeResponse.from(findMember);
            responseList.add(from);
        }
        return responseList;
    }
}
