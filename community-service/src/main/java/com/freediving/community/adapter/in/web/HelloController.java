package com.freediving.community.adapter.in.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1.0")
@RestController
public class HelloController {

	@GetMapping("/")
	public String hello() {
		return "Hello";
	}
}
