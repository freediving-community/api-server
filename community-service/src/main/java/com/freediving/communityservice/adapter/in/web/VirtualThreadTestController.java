// package com.freediving.communityservice.adapter.in.web;
//
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
//
// import lombok.RequiredArgsConstructor;
//
// @RestController
// @RequestMapping("/thread")
// @RequiredArgsConstructor
// public class VirtualThreadTestController {
//
// 	@GetMapping
// 	public ResponseEntity<ThreadInfo> getThreadInfo() throws InterruptedException {
// 		return ResponseEntity.ok(
// 			new ThreadInfo(
// 				Thread.currentThread().isVirtual(),
// 				Thread.currentThread().toString())
// 		);
// 	}
// 	public record ThreadInfo(boolean isVirtual, String threadName) {}
// }
