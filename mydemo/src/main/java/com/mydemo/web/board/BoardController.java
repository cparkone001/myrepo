package com.mydemo.web.board;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.mydemo.mapper.board.model.BoardInfoDto;
import com.mydemo.service.board.BoardInfoService;
import com.mydemo.web.board.dto.BoardWebDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	BoardInfoService boardInfoService;
	
    @GetMapping("/")
    public String boardList(Model model) {
    	
    	BoardInfoDto p = new BoardInfoDto();
    	List<BoardInfoDto> list = boardInfoService.getBoardInfoList(p);
    	
        model.addAttribute("boardList", list);
        return "pages/board/boardMain";
    }

    @GetMapping("/newBoardContent")
    public String newBoardContent(Model model) {
    	
    	return "pages/board/newBoardContent";
    }
    
    @GetMapping(value="/getBoardContent") 
	@ResponseBody
	public ModelAndView getBoardContent(@RequestParam("id") String id) { 	
		log.debug("U get move/boardContent");
		
    	BoardInfoDto p = new BoardInfoDto();
    	p.setSecnum(Long.parseLong(id));
    	List<BoardInfoDto> list = boardInfoService.getBoardInfoList(p);		
    	
    	ModelAndView mv = new ModelAndView("/pages/board/boardContent");
        mv.addObject("boardContent", list.get(0));
        return mv;
	}    

    @RequestMapping(value="/saveBoardInfo") 
    @ResponseBody
    public BoardWebDto saveBoardInfo(@RequestBody BoardWebDto dto) { 	
    	log.debug("U get move/list");
    	
    	BoardInfoDto p = new  BoardInfoDto();
    	p.setTitleNm(dto.getTitleNm());
    	p.setContentsCnt(dto.getContentsCnt());
    	Long secnum = boardInfoService.saveBoardInfo(p);
    	
    	BoardWebDto rt = BoardWebDto.builder().secnum(secnum).build();
        return rt;
    }

    @RequestMapping(value="/replyBoardInfo") 
    public ModelAndView replyBoardInfo(HttpServletRequest request) {
    	
    	log.debug("{}",request.getParameter("secnum"));
    	
    	String secnum      = request.getParameter("secnum");
    	String titleNm     = request.getParameter("titleNm");
    	String userNm      = request.getParameter("userNm");
    	String contentsCnt = request.getParameter("contentsCnt");
    	String depthNum    = request.getParameter("depthNum");
    	String refSecnum   = request.getParameter("refSecnum");
    	String refOrderNum = request.getParameter("refOrderNum");
    	
    	BoardInfoDto p = new BoardInfoDto();
    	p.setSecnum(Long.parseLong(secnum));
    	p.setTitleNm("Reply>"+ titleNm);
    	p.setUserNm("");
    	p.setContentsCnt("");
    	p.setDepthNum(Integer.parseInt(depthNum));
    	p.setRefSecnum(Long.parseLong(refSecnum));
    	p.setRefOrderNum(Integer.parseInt(refOrderNum));

    	ModelAndView mv = new ModelAndView("/pages/board/replyBoardContent");
        mv.addObject("boardContent", p);
        return mv; 	
    }

}