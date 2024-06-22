package com.mydemo.mapper.board;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mydemo.mapper.board.model.BoardInfoDto;

@Mapper
public interface BoardInfoMapper {
	List<BoardInfoDto> selectBoardInfo(BoardInfoDto p);
	int updateBoardInfo(BoardInfoDto p);
	int insertBoardInfo(BoardInfoDto p);
	int deleteBoardInfo(BoardInfoDto p);
}
