package com.mydemo.service.addr.dto;

import java.math.BigDecimal;
import java.util.List;

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
public class AddressInfoSvcDto {
	//DB에 관련 된 사항
	private String	adminDistrictCd; /*행정구역코드*/ 
	private String	firstStage; /*1단계*/ 
	private String	scecondStage; /*2단계*/ 
	private String	thirdStage; /*3단계*/ 
	private String	grdXVal; /*구획 X 좌표*/
	private String	grdYVal; /*구획Y 좌표*/
	private BigDecimal longitudeVal; /*경도값*/
	private BigDecimal latitudeVal; /*위도값*/
	
	private String addressName;
	
	//Kakao Api 사용에 관련 된 사항
	private MetaDto meta;
	private List<AddrDocument> documents;
}
