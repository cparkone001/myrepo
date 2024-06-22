package com.mydemo.service.weather.dto;

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
public class ItemDto {
	String baseDate;
	String baseTime;
	String category;
	String categoryName; //기상청 data 아님.
	String fcstDate;
	String fcstTime;
	String fcstValue;
	String nx;
	String ny;
}
