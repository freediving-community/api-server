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
 * 기본적으로 페이징을 한다고 가정을 하고 있다.
 *   => totalCount, count 페이지 정보를 지니고 있다.
 *
 * @author 조준희
 * @version 1.0.0
 * 작성일 2024-04-06
 **/
@Schema(description = "페이징이 있는 쿼리 응답 객체")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class QueryComponentListResponse {

	@Schema(description = "쿼리 컴포넌트 리스트", example = "[{...}, {...}]")
	private List<QueryComponent> components = new ArrayList<>();

	@Schema(description = "컴포넌트 총 개수", example = "100")
	private Long totalCount;

	@Schema(description = "요청한 페이지 번호", example = "1")
	private Integer page;

	@Schema(description = "페이지 당 요청한 카운트", example = "10")
	private Integer pageSize;
}
