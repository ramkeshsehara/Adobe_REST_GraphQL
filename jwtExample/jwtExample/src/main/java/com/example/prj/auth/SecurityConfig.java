package com.example.prj.auth;

import javax.crypto.SecretKey;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.prj.jwt.JwtConfig;
import com.example.prj.jwt.JwtTokenVerifier;
import com.example.prj.jwt.JwtUsernameAndPasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final SecretKey secretKey;
	private final JwtConfig jwtConfig;

	@Autowired
	public SecurityConfig(SecretKey secretKey, JwtConfig jwtConfig) {
		this.secretKey = secretKey;
		this.jwtConfig = jwtConfig;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.addFilter(
						new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(), jwtConfig, secretKey))
				.addFilterAfter(new JwtTokenVerifier(secretKey, jwtConfig),
						JwtUsernameAndPasswordAuthenticationFilter.class)
				.authorizeRequests().antMatchers("/admin").hasRole("ADMIN").antMatchers("/user")
				.hasAnyRole("USER", "ADMIN").anyRequest().authenticated();
	}

	@Autowired
	private DataSource dataSource; // pool of database connnections

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.jdbcAuthentication().dataSource(dataSource);
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

}