package com.delivery.tiago.domain.service.security.impl;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import com.delivery.tiago.domain.model.JwtUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenService {

	@Value("${jwt.expiration}")
	private String expiration;

	@Value("${jwt.secret}")
	private String secret;

	public String generateToken(Authentication authentication) {

		JwtUser usuario = (JwtUser) authentication.getPrincipal();

		Date now = new Date(System.currentTimeMillis());
		Date exp = new Date(now.getTime() + Long.parseLong(expiration));

		return Jwts.builder().setIssuer("IRS").setSubject(usuario.getId().toString()).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(exp).signWith(SignatureAlgorithm.HS256, secret).compact();
	}

	public boolean isTokenValid(String token) {
//		token.substring(7, token.length());
		try {
			Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	
	public Integer getTokenId(String token) {
		Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		return Integer.valueOf(body.getSubject());
	}


	public Integer getUsernameIdToken(String token) {
		
		String id;
		
		try {
			Claims claims = getClaimsFromToken(token);
			id = claims.getSubject();
		} catch (Exception e) {
			id = null;
		}
		
		
		return Integer.valueOf(id);
	}
	
	private Claims getClaimsFromToken(String token) throws AuthenticationException {
		
		Claims claims;
		try {
			claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		} catch (Exception e) {
			claims = null;
		}
		
		return claims;
	}

}
