package com.freediving.communityservice.adapter.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.freediving.communityservice.adapter.in.dto.ImageUploadRequest;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/api/v1.0/image")
@RestController
public class ImageCommandController {

	// return presigned url for image upload
	@PostMapping("/upload")
	public ResponseEntity<Long> getImagePresignedUrl(
		UserProvider userProvider,
		String accessToken,
		ImageUploadRequest imageUploadRequest
	) {
		return null;
/*
			'id' : 'UUID_16', // nanoId
			'imageServerId' : 'Image Server ID',
			'url' : 'https://hostname:port/{domain-service-name}/{object-id}/{image-id}', // 'https://cdndomain/community/boards/2/articles/23/Q2xs-tRl9-sElf-le1b'
			'width': '720',
			'height': '680',
			'style': '',
			'description':'단체사진~',
			'quality': '1.0',

			'serviceName':'community',
			'domainName': 'articles',
			'domainId': '521324', // articles/{521324}
			'originName': '2024_02_11_142343',
			'extension': 'jpg',
			'size': '1024',
			'secret': true
			'createdBy': '22091008',
			'createdAt': '',

	private String id;
	private String url;
	private Integer width;
	private Integer height;
	private String style;
	private String description;
	private String quality;
	private String serviceName;
	private String domainName;
	private String domainId;
	private String originName;
	private String extension;
	private Integer size;
	private boolean secret;
	private Long createdBy;
	private LocalDateTime createdAt;
*/
	}

}
