package com.freediving.authservice.adapter.out.external.aws;

import java.util.List;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.freediving.authservice.adapter.in.web.dto.CreateImgResponse;
import com.freediving.authservice.application.port.out.DeleteImgPort;
import com.freediving.authservice.application.port.out.ImgPort;
import com.freediving.authservice.config.AwsConfigProperties;
import com.freediving.authservice.util.ImgUtils;
import com.freediving.common.config.annotation.ExternalSystemAdapter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author         : sasca37
 * @Date           : 2024/01/21
 * @Description    : AWS S3에 이미지 정보를 전송하는 Adapter
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/01/21        sasca37       최초 생성
 */

@ExternalSystemAdapter
@RequiredArgsConstructor
@Slf4j
public class AwsImgExternalAdapter implements ImgPort, DeleteImgPort {
	private final AwsConfigProperties awsConfigProperties;
	private final AmazonS3 amazonS3;

	/**
	 * @Author           : sasca37
	 * @Date             : 2024/01/21
	 * @Param            : 이미지 경로 ( 디렉토리/파일명)
	 * @Return           : PreSigned URL 정보 반환
	 * @Description      : AWS Configuration 에 등록한 시크릿 정보를 바탕으로 PreSigned URL 생성 및 URL 정보 반환
	 */
	@Override
	public CreateImgResponse generatePreSignedUrl(String imgPath) {
		String bucket = getBucketName();

		GeneratePresignedUrlRequest generatePresignedUrlRequest = getPreSignedUrl(bucket, imgPath);
		String preSignedUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();
		String cdnUrl = ImgUtils.convertToCdnUrl(awsConfigProperties.s3().cloudFront(), preSignedUrl);
		return new CreateImgResponse(preSignedUrl, cdnUrl);
	}

	/**
	 * @Author           : sasca37
	 * @Date             : 2024/01/21
	 * @Param            : S3 버킷명, 파일명
	 * @Return           : GeneratePresignedUrlRequest
	 * @Description      : AWS S3 버킷에 이미지 업로드가 가능한 PreSigned URL 생성
	 * 					 1. 클라이언트 요청 HTTP 메서드를 PUT으로 설정 및 URL 만료 시간 설정 (2분)
	 * 					 2. 요청 헤더에 canned ACL(접근 제어 리스트)를 추가하여 업로드한 객체가 public read 권한을 갖도록 설정
	 */
	private GeneratePresignedUrlRequest getPreSignedUrl(String bucket, String uniqueImgName) {
		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, uniqueImgName)
			.withMethod(HttpMethod.PUT)
			.withExpiration(ImgUtils.getImgExpiration());
		generatePresignedUrlRequest.addRequestParameter(
			Headers.S3_CANNED_ACL,
			CannedAccessControlList.PublicRead.toString()
		);
		return generatePresignedUrlRequest;
	}

	/**
	 * @Author           : sasca37
	 * @Date             : 2024/03/06
	 * @Param            : 이미지 URL 리스트
	 * @Return           :
	 * @Description      : 서비스 간 요청 온 이미지 URL 리스트 정보를 삭제한다.
	 */
	@Override
	public void deleteImgList(List<String> imgUrlList) {
		String bucket = getBucketName();
		imgUrlList.forEach(url -> amazonS3.deleteObject(bucket, ImgUtils.parsingKeyImgUrl(url)));
	}

	private String getBucketName() {
		return awsConfigProperties.s3().bucket();
	}
}


