package com.freediving.common.domain.noti;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author : sasca37
 * @Date : 2024/07/23
 * @Description :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/07/23        sasca37       최초 생성
 */

@AllArgsConstructor
@Getter
public enum NotiCode {
    // MEMBER
    MEMBER_JOIN("MEMBER_JOIN", "신규 회원가입한 경우", "버디매칭 둘러보기, 스토리 둘러보기 이동 필요", false, null),
    MEMBER_POST_DEL("MEMBER_POST_DEL", "내 작성글 / 버디글이 관리자에 의해 삭제된 경우", "문의하기 이동 필요", false, null),
    MEMBER_LICENSE_REJECTED("MEMBER_LICENSE_REJECTED", "라이센스 심사 반려된 경우", "반려 사유 보기 팝업 필요, 변수 1개", true, "${rejectedContent}"),
    MEMBER_NO_LEVEL("MEMBER_NO_LEVEL", "라이센스 미등록 상태인 경우", "라이센스 등록하기 이동 필요", false, null)

    // STORY


    // BUDDY


    ;


    private final String linkCode;
    private final String title;
    private final String description;
    private final boolean pathVariableTf;
    private final String pathVariable;

}
