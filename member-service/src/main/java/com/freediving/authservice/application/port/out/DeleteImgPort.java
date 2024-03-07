package com.freediving.authservice.application.port.out;

import java.util.List;

/**
 * @Author         : sasca37
 * @Date           : 2024/03/03
 * @Description    : 이미지 삭제를 위한 Port
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/03/03        sasca37       최초 생성
 */
public interface DeleteImgPort {

	void deleteImgList(List<String> imgUrlList);
}
