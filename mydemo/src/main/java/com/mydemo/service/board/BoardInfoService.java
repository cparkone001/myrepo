package com.mydemo.service.board;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mydemo.infra.util.UUIDGenerateUtils;
import com.mydemo.mapper.board.BoardInfoMapper;
import com.mydemo.mapper.board.model.BoardInfoDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardInfoService {
	
	@Autowired
	BoardInfoMapper  boardInfoMapper;
	
	public List<BoardInfoDto> getBoardInfoList(BoardInfoDto p) {
		return boardInfoMapper.selectBoardInfo(p);
	}

	public Long saveBoardInfo(BoardInfoDto p) {
		
		BoardInfoDto parm = new BoardInfoDto();
		String uuid = UUIDGenerateUtils.makeShortUUID();
		Long secNum = Long.parseLong(uuid);
		parm.setSecnum(secNum);
		parm.setDepthNum(1);
		parm.setRefOrderNum(0);
		parm.setRefSecnum(0L);
		parm.setTitleNm(p.getTitleNm());
		parm.setContentsCnt(p.getContentsCnt());
		
		int cnt = boardInfoMapper.insertBoardInfo(parm);
		
		return secNum;
	}

	public List<BoardInfoDto> saveReplyBoardInfo(BoardInfoDto p) {
		
		BoardInfoDto parm = new BoardInfoDto();
		String uuid = UUIDGenerateUtils.makeShortUUID();
		Long secNum = Long.parseLong(uuid);
		parm.setSecnum(secNum);
		parm.setDepthNum(p.getDepthNum() + 1);
		parm.setRefOrderNum(p.getRefOrderNum()+1);
		parm.setRefSecnum(p.getSecnum());
		parm.setTitleNm(p.getTitleNm());
		parm.setContentsCnt(p.getContentsCnt());
		int cnt = boardInfoMapper.insertBoardInfo(parm);
		return boardInfoMapper.selectBoardInfo(p);
	}
}
