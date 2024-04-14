package com.freediving.communityservice.adapter.out.dto.image;

public record ImageBriefResponse(
	int sortNumber,
	String url,
	int imageTotalCount
) {
}
