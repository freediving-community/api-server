package org.freediving.exception;

/**
 * @Author         : sasca37
 * @Date           : 2024/02/08
 * @Description    : Gateway 내 JWT 검증 오류 처리를 공통화 하기 위한 record 클래스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/02/08        sasca37       최초 생성
 */
public record GatewayException(int code, String data, String msg) {
}
