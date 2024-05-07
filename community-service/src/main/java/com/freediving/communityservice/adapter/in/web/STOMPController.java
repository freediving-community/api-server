package com.freediving.communityservice.adapter.in.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller("/ws")
public class STOMPController {
	@GetMapping("/test")
	public ModelAndView getStompTestPage() {
		return new ModelAndView("stompTest");
	}
}
