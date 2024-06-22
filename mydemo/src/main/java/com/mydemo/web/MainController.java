package com.mydemo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MainController {

    @GetMapping("/")
    public String selectImageList(Model model) {
        model.addAttribute("title", "");
        return "pages/test/testPage";
    }

	@RequestMapping(value="/movie/list")  
	public String list(Model model) { 	
		log.debug("U get move/list");
		return "/pages/test/list";
	}    
    
}