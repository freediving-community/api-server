package com.freediving.buddyservice.adapter.in.web.v1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloControllerV1 {

	@GetMapping("/ping")
	public String hello() {
		return "pong";
	}
}
