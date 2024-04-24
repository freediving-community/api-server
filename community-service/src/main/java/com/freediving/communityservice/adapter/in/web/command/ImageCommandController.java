// package com.freediving.communityservice.adapter.in.web.command;
//
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;
//
// import com.freediving.communityservice.adapter.in.dto.ImageUploadRequest;
// import com.freediving.communityservice.adapter.in.web.UserProvider;
// import com.freediving.communityservice.application.port.in.ImageUseCase;
//
// import lombok.RequiredArgsConstructor;
//
// @RequiredArgsConstructor
// @RequestMapping("/api/v1/image")
// @RestController
// public class ImageCommandController {
//
// 	private final ImageUseCase imageUseCase;
//
//
// 	@PostMapping("/upload")
// 	public ResponseEntity<String> getImagePresignedUrl(
// 		UserProvider userProvider,
// 		String accessToken,
// 		ImageUploadRequest imageUploadRequest
// 	) {
//
// 		// String signedUrl = imageUseCase.getPresignedUrl(ImageUploadCommand.builder()
// 		// 	.userProvider(userProvider)
// 		// 	.width(imageUploadRequest.getWidth())
// 		// 	.height(imageUploadRequest.getHeight())
// 		// 	.style(imageUploadRequest.getStyle())
// 		// 	.description(imageUploadRequest.getDescription())
// 		// 	.originName(imageUploadRequest.getOriginName())
// 		// 	.extension(imageUploadRequest.getExtension())
// 		// 	.size(imageUploadRequest.getSize())
// 		// 	.build());
//
// 		return null;
// /*
// 			'id' : 'UUID_16', // nanoId
// 			'imageServerId' : 'Image Server ID',
// 			'url' : 'https://hostname:port/{domain-service-name}/{object-id}/{image-id}', // 'https://cdndomain/community/boards/2/articles/23/Q2xs-tRl9-sElf-le1b'
// 			'width': '720',
// 			'height': '680',
// 			'style': '',
// 			'description':'단체사진~',
// 			'quality': '1.0',
//
// 			'serviceName':'community',
// 			'domainName': 'articles',
// 			'domainId': '521324', // articles/{521324}
// 			'originName': '2024_02_11_142343',
// 			'extension': 'jpg',
// 			'size': '1024',
// 			'secret': true
// 			'createdBy': '22091008',
// 			'createdAt': '',
//
// 	private String id;
// 	private String url;
// 	private Integer width;
// 	private Integer height;
// 	private String style;
// 	private String description;
// 	private String quality;
// 	private String serviceName;
// 	private String domainName;
// 	private String domainId;
// 	private String originName;
// 	private String extension;
// 	private Integer size;
// 	private boolean secret;
// 	private Long createdBy;
// 	private LocalDateTime createdAt;
// */
// 	}
//
// }
