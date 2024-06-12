package com.freediving.buddyservice.domain.query;

import java.util.ArrayList;
import java.util.List;

import com.freediving.buddyservice.domain.query.component.QueryComponent;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * QueryComponent객체를 List형식으로 Response할떄 사용하는 DTO이다.
 * @author 조준희
 * @version 1.0.0
 * 작성일 2024-04-06
 **/
@Schema(description = "페이징이 없는 쿼리 응답 객체")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryComponentListWithoutPageResponse {

	@Schema(description = "쿼리 컴포넌트 리스트", example = "[{...}, {...}]")
	private List<QueryComponent> components = new ArrayList<>();
}
