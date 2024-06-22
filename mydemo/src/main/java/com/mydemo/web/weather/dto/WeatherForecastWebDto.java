package com.mydemo.web.weather.dto;

import java.math.BigDecimal;
import java.util.List;

import com.mydemo.mapper.student.model.StudentInfoDto;
import com.mydemo.service.weather.dto.ItemDto;
import com.mydemo.web.student.dto.StudentInfoWebDto;

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
public class WeatherForecastWebDto {
	//요청값
	String serviceKey ;
	String pageNo; /*페이지번호*/
	String numOfRows; /*한 페이지 결과 수*/
	String dataType; /*요청자료형식(XML/JSON) Default: XML*/
	String baseDate; /*yyyymmdd*/
	String baseTime; /*hhmm*/
	String nx; /*예보지점의 X 좌표값*/
	String ny; /*예보지점의 Y 좌표값*/
	
	//주소 서비스 사용할때
	BigDecimal latitude;
	BigDecimal longitude;
	
	String addressName;
	
	List<ItemDto> itemList;
}
