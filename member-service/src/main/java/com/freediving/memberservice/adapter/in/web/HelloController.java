package com.freediving.memberservice.adapter.in.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Tag(name = "Ping", description = "MemberService Health Check")
public class HelloController {

	@GetMapping("/")
	public String hello() {
		return "Hello";
	}
}
