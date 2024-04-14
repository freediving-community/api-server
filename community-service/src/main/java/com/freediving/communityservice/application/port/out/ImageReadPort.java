package com.freediving.communityservice.application.port.out;

import java.util.List;

import com.freediving.communityservice.adapter.out.dto.image.ImageResponse;

public interface ImageReadPort {
	List<ImageResponse> getImageListByArticle(Long articleId);
}
