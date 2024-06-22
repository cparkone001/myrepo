package com.mydemo.web.board.dto;

import java.util.List;

import com.mydemo.mapper.board.model.BoardInfoDto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class BoardWebDto {

	String id;

	Long 	secnum       ;    /*일련번호*/
	Long 	refSecnum    ;    /*참조일련번호*/
	Integer depthNum     ;    /*답변깊이*/
	Integer refOrderNum  ;    /*답변순서*/
	String 	titleNm      ;    /*제목명*/
	String 	contentsCnt  ;    /*내용*/
	String 	userId       ;    /*사용자id*/
	String 	userNm       ;    /*사용자명*/
	String 	userIpAddrNm ;    /*작성자Ip명*/
	String 	emailAddrNm  ;    /*이메이일주소명*/	
	
	List<BoardInfoDto> boardList; 
}
