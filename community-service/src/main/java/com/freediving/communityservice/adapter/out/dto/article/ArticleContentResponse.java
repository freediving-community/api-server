// package com.freediving.communityservice.adapter.out.dto.article;
//
// import java.time.LocalDateTime;
// import java.util.List;
//
// import com.freediving.communityservice.adapter.out.dto.image.ImageResponse;
// import com.freediving.communityservice.adapter.out.dto.user.UserInfo;
//
// import lombok.Builder;
// import lombok.Data;
// import lombok.EqualsAndHashCode;
//
// @Data
// @EqualsAndHashCode(callSuper = true)
// public class ArticleContentResponse extends ArticleBaseResponse {
//
// 	private UserInfo userInfo;
// 	private List<ImageResponse> images;
//
// 	@Builder
// 	public ArticleContentResponse(Long articleId, String title, String content, LocalDateTime createdAt, Long createdBy,
// 		int viewCount, int likeCount, int commentCount, UserInfo userInfo, List<ImageResponse> images) {
//
// 		super(articleId, title, content, createdAt, createdBy, viewCount, likeCount, commentCount);
// 		this.userInfo = userInfo;
// 		this.images = images;
// 	}
// }
