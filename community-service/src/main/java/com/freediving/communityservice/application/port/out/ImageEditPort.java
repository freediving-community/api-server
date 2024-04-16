package com.freediving.communityservice.application.port.out;

import java.util.List;
import java.util.Map;

import com.freediving.communityservice.adapter.out.dto.image.ImageResponse;

public interface ImageEditPort {
	List<ImageResponse> editAllImageSortNumber(Long articleId, Map<String, ImageResponse> imageMapToUpdate);
}
