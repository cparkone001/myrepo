package com.mydemo.service.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mydemo.mapper.student.StudentInfoMapper;
import com.mydemo.mapper.student.model.StudentInfoDto;

@Service
public class StudentService {
	
	@Autowired
	StudentInfoMapper studentInfoMapper;
	
	public List<StudentInfoDto> getStudentInfo(StudentInfoDto p) {
		return studentInfoMapper.selectStudentInfo(p);
	}
	/**
	 * 등록
	 * @param p
	 * @return
	 */
	public int registerStudentInfo(StudentInfoDto p) {
		return studentInfoMapper.insertStudentInfo(p);
	}
	/**
	 * 수정
	 * @param p
	 * @return
	 */
	public int modifyStudentInfo(StudentInfoDto p) {
		return studentInfoMapper.updateStudentInfo(p);
	}
	/**
	 * 삭제
	 * @param p
	 * @return
	 */
	public int removeStudentInfo(StudentInfoDto p) {
		return studentInfoMapper.deleteStudentInfo(p);
	}
}
