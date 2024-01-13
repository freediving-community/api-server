package org.freediving.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
	@GetMapping("/health")
	public ResponseEntity<Object> healthCheck(){
		return ResponseEntity.status(200).build();
	}
}
