package com.post.service.like;

import com.post.api.like.request.LikeRequest;
import com.post.api.like.response.LikeResponse;
import com.post.api.like.response.MembersWithLikeResponse;
import com.post.domain.board.Board;
import com.post.domain.like.Likes;
import com.post.domain.member.Member;
import com.post.repository.board.BoardRepository;
import com.post.repository.likes.LikeRepository;
import com.post.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    /**
     * 좋아요를 저장하는 과정
     * 좋아요를 요청할땐, 로그인 상태이므로 memberId와 해당 페이지의 boardId가 요청으로 넘어온다
     * 1. 어떤 게시글에 좋아요를 누르는지 boardId가 필요
     * 2. 누가 게시글에 좋아요를 누르는지 memberId가 필요
     * 3. 좋아요 시간을 지정해서 DB에 저장해야하므로 서비스 계층에서 now()로 시간을 설정해준뒤 Repository로 넘긴다.
     */
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

    /**
     * 해당 게시글에 좋아요를 누른 회원조회
     */
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
