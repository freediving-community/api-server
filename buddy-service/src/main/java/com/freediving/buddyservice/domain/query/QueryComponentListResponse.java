package com.freediving.buddyservice.domain.query;

import java.util.ArrayList;
import java.util.List;

import com.freediving.buddyservice.domain.query.component.QueryComponent;



/**
* QueryComponent객체를 List형식으로 Response할떄 사용하는 DTO이다.
 * 기본적으로 페이징을 한다고 가정을 하고 있다.
 *   => totalCount, count 페이지 정보를 지니고 있다.
*
* @author 조준희
* @version 1.0.0
* 작성일 2024-04-06
**/
public class QueryComponentListResponse {

	private List<QueryComponent> components = new ArrayList<>();
	// 컴포넌트 총 개수.
	private Integer totalCount; //todo 페이징 부가 정보들은 추후 협의 후에 추가 해야함.
}
