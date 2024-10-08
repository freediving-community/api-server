package com.freediving.authservice.util;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

/**
 * @Author         : sasca37
 * @Date           : 2023/12/31
 * @Description    : Jwt 토큰 생성 Util
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * ===========================================================
 * 2023/12/31        sasca37       최초 생성
 */
public class JwtTokenUtils {

	private static final long EXPIRED_ACCESS_TIME = 5 * 60 * 60 * 1000L;
	private static final long EXPIRED_REFRESH_TIME = 30 * 24 * 60 * 60 * 1000L;
	private static final long ALLOW_SAMPLE_EXPIRED_TIME = 10 * 365 * 24 * 60 * 60 * 1000L;

	public static String generateAccessToken(String userId, String oauthType, String key) {
		return generateToken(userId, oauthType, key, EXPIRED_ACCESS_TIME);
	}

	public static String generateRefreshToken(String userId, String oauthType, String key) {
		return generateToken(userId, oauthType, key, EXPIRED_REFRESH_TIME);
	}

	private static Key getKey(String key) {
		byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	private static String generateToken(String userId, String oauthType, String key, long expiredTime) {
		Claims claims = Jwts.claims();
		claims.put("userId", userId);
		claims.put("oauthType", oauthType);
		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + expiredTime))
			.signWith(getKey(key), SignatureAlgorithm.HS256)
			.compact();
	}

	public static String extractOauthType(String token, String key) {
		Claims claims = extractClaims(token, key);
		String oauthType = claims.get("oauthType", String.class);
		return oauthType;
	}

	private static Claims extractClaims(String token, String key) {
		return Jwts.parserBuilder().setSigningKey(getKey(key))
			.setAllowedClockSkewSeconds(ALLOW_SAMPLE_EXPIRED_TIME)
			.build().parseClaimsJws(token).getBody();
	}
}
