package com.delivery.tiago.domain.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {
	
	/**
	 * Field to represent the claim key username
	 */
	private static final String CLAIM_KEY_USERNAME = "sub";
	
	/**
	 * Field to represent the claim key role
	 */
	private static final String CLAIM_KEY_ROLE = "role";
	
	/**
	 * Field to represent the claim key created
	 */
	private static final String CLAIM_KEY_CREATED = "created";

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;


	public String getUsernameFromToken(String token) {
		
		String username;
		
		try {
			Claims claims = getClaimsFromToken(token);
			username = claims.getSubject();
		} catch (Exception e) {
			username = null;
		}
		
		return username;
	}


	public Date getExpirationDateFromToken(String token) {
		
		Date expiration;
		
		try {
			Claims claims = getClaimsFromToken(token);
			expiration = claims.getExpiration();
		} catch (Exception e) {
			expiration = null;
		}
		
		return expiration;
	}


	public boolean validToken(String token) {
		return !expiredToken(token);
	}
  
	
	public String getToken(UserDetails userDetails) {

		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());
		claims.put(CLAIM_KEY_CREATED, new Date());
		userDetails.getAuthorities().forEach(authority -> claims.put(CLAIM_KEY_ROLE, authority.getAuthority()));

		return generateToken(claims);
	}
	
	
	public String getToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		claims.put(CLAIM_KEY_USERNAME, username);
		claims.put(CLAIM_KEY_CREATED, new Date());

		return generateToken(claims);
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


	private Date generateExpirationDate() {
		return new Date(System.currentTimeMillis() + expiration * 1000);
	}
	

	private boolean expiredToken(String token) {
		Date expirationDate = this.getExpirationDateFromToken(token);
		if (expirationDate == null) {
			return false;
		}
		return expirationDate.before(new Date());
	}
	

	private String generateToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setExpiration(generateExpirationDate())
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
}
