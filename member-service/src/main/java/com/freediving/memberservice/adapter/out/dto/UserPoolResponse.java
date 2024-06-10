package com.freediving.memberservice.adapter.out.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
@NoArgsConstructor
public class UserPoolResponse {
	private List<String> divingPools;
}
