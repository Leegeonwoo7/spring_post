package com.post.repository.mybatis;

import com.post.api.board.request.SearchCond;
import com.post.domain.board.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    List<Board> findByCondition(SearchCond condition);
}
