package com.freediving.memberservice.application.port.in;

import java.util.Random;

import org.apache.commons.lang3.StringUtils;

/**
 * @Author         : sasca37
 * @Date           : 2024/02/27
 * @Description    : 신규 가입 시 닉네임 생성 기능을 담당하는 클래스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2024/02/27        sasca37       최초 생성
 */
public class NicknameGenerator {
	private static final String[] WORDS = {
		"빠른", "느린", "높은", "낮은", "멋진",
		"작은", "애기", "어른", "예쁜", "얇은",
		"행복", "슬픈", "기쁨", "쿨한", "초보",
		"고래", "사슴", "토끼", "거북", "상어",
		"문어", "해마", "가재", "갈치", "물범",
		"펭귄", "참치", "새우", "홍합", "해달"
	};

	private static final Random RANDOM = new Random();

	public static String generateNickname(Long id) {
		String sequence = StringUtils.leftPad(String.valueOf(id), 5, '0');
		String randomAdjective = WORDS[RANDOM.nextInt(WORDS.length)];
		return randomAdjective + "다이버_" + sequence;
	}
}
