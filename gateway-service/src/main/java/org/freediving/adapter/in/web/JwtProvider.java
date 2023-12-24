package org.freediving.adapter.in.web;

import java.nio.charset.StandardCharsets;
import java.security.Key;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtProvider {

	public static String getExpiredOrMalformedMessage(String token, String key) {
		try {
			Jwts.parserBuilder().setSigningKey(getKey(key)).build().parseClaimsJws(token).getBody();
			return null;
		} catch (MalformedJwtException | SignatureException e) {
			log.error("MalformedJwtException : {}", e.getMessage());
			return AuthorizationFilter.INVALID_JWT_TOKEN;
		} catch (ExpiredJwtException e) {
			log.error("ExpiredJwtException : {}", e.getMessage());
			return AuthorizationFilter.EXPIRED_JWT_TOKEN;
		}
	}

	private static Key getKey(String key) {
		byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
		return Keys.hmacShaKeyFor(keyBytes);
	}
}

