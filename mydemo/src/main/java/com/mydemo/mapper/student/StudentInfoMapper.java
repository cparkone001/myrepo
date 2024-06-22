package com.mydemo.mapper.student;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mydemo.mapper.student.model.StudentInfoDto;
/**
 * 
 */
@Mapper
public interface StudentInfoMapper {
	List<StudentInfoDto> selectStudentInfo(StudentInfoDto p);
	int updateStudentInfo(StudentInfoDto p);
	int insertStudentInfo(StudentInfoDto p);
	int deleteStudentInfo(StudentInfoDto p);
}
