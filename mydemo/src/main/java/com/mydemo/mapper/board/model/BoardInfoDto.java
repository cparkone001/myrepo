package com.mydemo.mapper.board.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 게시판 Dto  
 * @author 박철
 * @since 2024.04.10
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class BoardInfoDto {
	Long 	secnum       ;    /*참조일련번호*/
	Long 	refSecnum    ;    /*참조일련번호*/
	Integer depthNum     ;    /*답변깊이*/
	Integer refOrderNum  ;    /*답변순서*/
	String 	titleNm      ;    /*제목명*/
	String 	contentsCnt  ;    /*내용*/
	String 	userId       ;    /*사용자id*/
	String 	userNm       ;    /*사용자명*/
	String 	userIpAddrNm ;    /*작성자Ip명*/
	String 	emailAddrNm  ;    /*이메이일주소명*/
	String 	regUsrId     ;    /*등록자id*/
	String 	uptUsrId     ;    /*변경자id*/
	String  postDate	 ;    /*등록일*/
}
