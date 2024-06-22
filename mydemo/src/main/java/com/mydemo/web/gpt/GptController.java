package com.mydemo.web.gpt;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/gpt")
public class GptController {
	
    @GetMapping("/gptPage")
    public String studentPage() {
    	return "/pages/gpt/GptView";
    }

}
