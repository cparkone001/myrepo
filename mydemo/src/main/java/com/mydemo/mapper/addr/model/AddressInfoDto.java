package com.mydemo.mapper.addr.model;

import java.math.BigDecimal;

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
public class AddressInfoDto {

	private String	adminDistrictCd; /*행정구역코드*/ 
	private String	firstStage; /*1단계*/ 
	private String	scecondStage; /*2단계*/ 
	private String	thirdStage; /*3단계*/ 
	private String	grdXVal; /*구획 X 좌표*/
	private String	grdYVal; /*구획Y 좌표*/
	private BigDecimal longitudeVal; /*경도값*/
	private BigDecimal latitudeVal; /*위도값*/
}
