package com.freediving.communityservice.adapter.in.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1.0")
@RestController
public class BoardController {
	//TODO SelfValidating https://velog.io/@byeongju/Bean-Validation-%EB%B6%88%ED%8E%B8%ED%95%A8-%ED%95%B4%EA%B2%B0%ED%95%98%EA%B8%B0
	public static class SampleResponse {
		String sample;
	}

	@GetMapping("/boards")
	public ResponseEntity<SampleResponse> getBoardList() {
		SampleResponse res = new SampleResponse();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@GetMapping("/boards/{boardId}")
	public ResponseEntity<SampleResponse> getBoardDetail(@PathVariable Long boardId) {
		SampleResponse res = new SampleResponse();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

	@PostMapping("/boards")
	public ResponseEntity<SampleResponse> createBoard() {
		SampleResponse res = new SampleResponse();
		return new ResponseEntity<>(res, HttpStatus.OK);
	}

}
