package com.freediving.authservice.application.port.in;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import com.freediving.authservice.domain.OauthType;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

public class JwtTokenUtils {

	private static final long EXPIRED_ACCESS_TIME = 5 * 60 * 60 * 1000L;
	private static final long EXPIRED_REFRESH_TIME = 30 * 24 * 60 * 60 * 1000L;

	public static String generateAccessToken(OauthType oauthType, String email, String key) {
		return generateToken(oauthType, email, key, EXPIRED_ACCESS_TIME);
	}

	public static String generateRefreshToken(OauthType oauthType, String email, String key) {
		return generateToken(oauthType, email, key, EXPIRED_REFRESH_TIME);
	}

	private static Key getKey(String key) {
		byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBytes);
	}

	private static String generateToken(OauthType oauthType, String email, String key, long expiredTime) {
		Claims claims = Jwts.claims();
		claims.put("oauthType", oauthType.name());
		claims.put("email", email);
		return Jwts.builder()
			.setClaims(claims)
			.setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + expiredTime))
			.signWith(getKey(key), SignatureAlgorithm.HS256)
			.compact();
	}
}
