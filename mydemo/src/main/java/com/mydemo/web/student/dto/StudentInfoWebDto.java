package com.mydemo.web.student.dto;

import java.util.List;

import com.mydemo.mapper.student.model.StudentInfoDto;

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
public class StudentInfoWebDto {
	String studentNum;/* 학번 */
	String name; /* 이름 */
	Integer gradeNum;/* 학년 */
	String addr; /* 주소 */

	List<StudentInfoDto> dataList;
}
