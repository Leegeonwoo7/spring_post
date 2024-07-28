package com.post.app.repository.mybatis;

import com.post.app.api.board.request.SearchCond;
import com.post.app.domain.board.Board;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {

    List<Board> findByCondition(SearchCond condition);
}
