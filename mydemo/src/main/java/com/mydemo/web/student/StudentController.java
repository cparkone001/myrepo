package com.mydemo.web.student;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mydemo.mapper.student.model.StudentInfoDto;
import com.mydemo.service.student.StudentService;
import com.mydemo.web.student.dto.StudentInfoWebDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/student")
public class StudentController {
	
	@Autowired
	StudentService studentService;

    @GetMapping(value="/getStudentlist") 
	@ResponseBody
	public ModelAndView getStudentlist() { 	
		log.debug("U get getStudentlist");
		
		StudentInfoDto p = new StudentInfoDto();
    	//p.setStudentNum(id);
    	List<StudentInfoDto> list = studentService.getStudentInfo(p);
    	
    	ModelAndView mv = new ModelAndView("/pages/student/list");
        mv.addObject("studentList", list);
        return mv;
	}    

    @GetMapping("/studentPage")
    public String studentPage() {
    	return "/pages/student/list1";
    }
    
    /**
     * 학생정보 관리 페이지 이동
     * @return
     */
    @GetMapping("/registerPage")
    public String registerPage() {
    	return "/pages/student/register";
    }
    
    /**
     * 학생정보 리스트 조회
     * @return
     */
    @RequestMapping(value="/studentList") 
    @ResponseBody
    public StudentInfoWebDto getStudentList() { 	
    	log.debug("U get getStudentlist1");
    	
    	StudentInfoDto p = new StudentInfoDto();
    	List<StudentInfoDto> list = studentService.getStudentInfo(null);

    	StudentInfoWebDto rt = new StudentInfoWebDto();
    	rt.setDataList(list);
    	
    	return rt;
    }        
    
    /**
     * 학생정보 등록
     * @param parm
     * @return
     */
    @RequestMapping(value="/registerStudent") 
    @ResponseBody
    public StudentInfoWebDto registerStudent(@RequestBody StudentInfoWebDto parm) { 	
    	log.debug("U get registerStudent");
    	
    	StudentInfoDto p = new StudentInfoDto();
    	p.setStudentNum(parm.getStudentNum());
    	p.setName(parm.getName());
    	p.setGradeNum(parm.getGradeNum());
    	p.setAddr(parm.getAddr());
    	studentService.registerStudentInfo(p);
    	
    	List<StudentInfoDto> list = studentService.getStudentInfo(null);

    	StudentInfoWebDto rt = new StudentInfoWebDto();
    	rt.setDataList(list);
    	
    	return rt;
    }       

    /**
     * 학생정보 수정
     * @param parm
     * @return
     */
    @RequestMapping(value="/modifyStudent") 
    @ResponseBody
    public StudentInfoWebDto modifyStudent(@RequestBody StudentInfoWebDto parm) { 	
    	log.debug("U get modifyStudent");
    	
    	StudentInfoDto p = new StudentInfoDto();
    	p.setStudentNum(parm.getStudentNum());
    	p.setName(parm.getName());
    	p.setGradeNum(parm.getGradeNum());
    	p.setAddr(parm.getAddr());
    	studentService.modifyStudentInfo(p);
    	
    	List<StudentInfoDto> list = studentService.getStudentInfo(null);
    	
    	StudentInfoWebDto rt = new StudentInfoWebDto();
    	rt.setDataList(list);
    	
    	return rt;
    }       

    /**
     * 학생정보 삭제
     * @param parm
     * @return
     */
    @RequestMapping(value="/removeStudent") 
    @ResponseBody
    public StudentInfoWebDto removeStudent(@RequestBody StudentInfoWebDto parm) { 	
    	log.debug("U get removeStudent");
    	
    	StudentInfoDto p = new StudentInfoDto();
    	p.setStudentNum(parm.getStudentNum());
    	studentService.removeStudentInfo(p);
    	
    	List<StudentInfoDto> list = studentService.getStudentInfo(null);
    	
    	StudentInfoWebDto rt = new StudentInfoWebDto();
    	rt.setDataList(list);
    	
    	return rt;
    }       
    
    @ResponseBody
    @PostMapping("/register")
    //public ModelAndView requestParamMap(@RequestParam Map<String, Object> paramMap) {
    public ModelAndView requestParamMap(@RequestParam("studentNum") String studentNum, @RequestParam("name") String name, @RequestParam("gradeNum") String gradeNum, @RequestParam("addr") String addr) {
    	//log.info("studentNum={}, name={}  gradeNum={} ,addr={}", paramMap.get("studentNum"), paramMap.get("name"), paramMap.get("gradeNum"),paramMap.get("addr"));
    	log.debug("studentNum={}, name={}  gradeNum={} ,addr={}", studentNum, name, gradeNum,addr);
    	
    	StudentInfoDto p = new StudentInfoDto();
    	p.setStudentNum(studentNum);
    	p.setName(name);
    	p.setGradeNum(Integer.parseInt(gradeNum));
    	p.setAddr(addr);
    	studentService.registerStudentInfo(p);
		
    	//p.setStudentNum(id);
    	List<StudentInfoDto> list = studentService.getStudentInfo(null);  	
    	ModelAndView mv = new ModelAndView("/pages/student/list");
        mv.addObject("studentList", list);
        return mv;
    }    
    
}
