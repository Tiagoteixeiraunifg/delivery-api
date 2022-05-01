package com.delivery.tiago.api.filters;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import com.delivery.tiago.domain.model.JwtUser;
import com.delivery.tiago.domain.model.JwtUserFactory;
import com.delivery.tiago.domain.model.User;
import com.delivery.tiago.domain.repository.UserRepository;
import com.delivery.tiago.domain.service.security.impl.TokenService;


public class TokenAuthenticationFilter extends OncePerRequestFilter {
	
	private final TokenService tokenService;
	private final UserRepository repository;
		
	@Value("${jwt.secret}")
	private String secret;
	
	public TokenAuthenticationFilter(TokenService tokenService, UserRepository repository) {
		this.tokenService = tokenService;
		this.repository = repository;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String tokenFromHeader = getTokenFromHeader(request);
		boolean tokenValid = tokenService.isTokenValid(tokenFromHeader);
		if(tokenValid) {
			this.authenticate(tokenFromHeader);
		}

		filterChain.doFilter(request, response);
	}

	private void authenticate(String tokenFromHeader) {
		
		Integer idUser =  tokenService.getUsernameIdToken(tokenFromHeader);
		
		Optional<User> optionalUser = repository.findById(idUser);
		
		if(optionalUser.isPresent()) {
			
			JwtUser jwtUser = JwtUserFactory.create(optionalUser.get());
						
			UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = 
					new UsernamePasswordAuthenticationToken(jwtUser, null, jwtUser.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		}
	}

	private String getTokenFromHeader(HttpServletRequest request) {
		String token = request.getHeader("Authorization");
		if(token == null || token.isEmpty() || !token.startsWith("Bearer ")) {
			return null;
		}
		
		return token.substring(7, token.length());
	}
	}

