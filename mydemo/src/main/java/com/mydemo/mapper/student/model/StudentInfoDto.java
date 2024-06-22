package com.mydemo.mapper.student.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * 학생정보 Dto  
 * @author 박철
 * @since 2024.05.06
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class StudentInfoDto {
	String studentNum ;/*학번*/
	String name       ; /*이름*/
	Integer gradeNum   ;/*학년*/
	String addr       ; /*주소*/	
}
