package com.freediving.memberservice.adapter.out.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @Author         : sasca37
 * @Date           : 2024/05/25
 * @Description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/05/25        sasca37       최초 생성
 */
@AllArgsConstructor
@Getter
@Builder
public class UserConceptRequest {
	private Long userId;
  	private List<String> preferredConcepts;
}
