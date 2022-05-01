package com.delivery.tiago.common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.delivery.tiago.api.filters.JwtAuthenticationEntryPointFilter;
import com.delivery.tiago.api.filters.TokenAuthenticationFilter;
import com.delivery.tiago.domain.repository.UserRepository;
import com.delivery.tiago.domain.service.security.impl.JwtUserDetailsServiceImpl;
import com.delivery.tiago.domain.service.security.impl.TokenService;




@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private JwtUserDetailsServiceImpl authenticationService;
	
	@Autowired
	private JwtAuthenticationEntryPointFilter unauthorizedHandler;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private UserRepository repository;
	
	@Value("${spring.profiles.active}")
	private String userProfile;
	
	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManager();
	}
	
    //Configurations for authentication
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    	auth.userDetailsService(authenticationService).passwordEncoder(new BCryptPasswordEncoder());
    }

    //Configuration for authorization
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	http.authorizeRequests().antMatchers("/api/v1/user/auth/**", "/api/v1/user/**", "/configuration/security", "/webjars/**", 
    			"/v2/api-docs", "/swagger-resources/**", "/swagger-ui/**", "/manage/**").permitAll()
    	.anyRequest().authenticated()
    	.and().csrf().disable().exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
    	.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    	.and().addFilterBefore(new TokenAuthenticationFilter(tokenService, repository), UsernamePasswordAuthenticationFilter.class);

    }

    //Ingnorando a interceptação das requisições no Swagger2
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs",
                                   "/configuration/ui",
                                   "/swagger-resources/**",
                                   "/configuration/security",
                                   "/swagger-ui.html",
                                   "/webjars/**");
    }
}
