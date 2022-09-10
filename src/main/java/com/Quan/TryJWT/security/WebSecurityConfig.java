package com.Quan.TryJWT.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.Quan.TryJWT.security.jwt.AuthEntryPointJwt;
import com.Quan.TryJWT.security.jwt.AuthTokenFilter;
import com.Quan.TryJWT.security.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
		// securedEnabled = true,
		// jsr250Enabled = true,
		prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;
	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}
	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		// nếu userDetailsService không trả đúng dữ liệu sẽ bị sai
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
			// authenticationEntryPoint nhận lỗi nếu bên AuthEntryPointJwt trả lỗi về, lỗi xác thực thất bại (AuthEntryPointJwt extend authenticationEntryPoint)
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and() // AuthenticationEntryPoint will catch authentication error.
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests().antMatchers("/api/auth/**").permitAll()
			.antMatchers("/api/posters/**").permitAll()
			.antMatchers("/api/category/**").permitAll()
			.antMatchers("/api/order/**").permitAll()
			.antMatchers("/api/cart/**").permitAll()
			.antMatchers("/api/brand/**").permitAll()
			.antMatchers("/api/product/**").permitAll()
			.antMatchers("/api/cart/**").permitAll()
			.antMatchers("/api/order/**").permitAll()
			.antMatchers("/api/feedbacks/**").permitAll()
			.antMatchers("/api/users/**").permitAll()
			.antMatchers("/api/address/**").permitAll()
			.antMatchers("/api/test/**").permitAll()
			.antMatchers("/products/**").permitAll()
			.antMatchers("/users/**").permitAll()
			.antMatchers("/api/admin/**").permitAll()
			.antMatchers("/api/verify/**").permitAll()

			.anyRequest().authenticated();
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
	}
	
	@Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/v2/api-docs", "/configuration/**", "/swagger-resources/**",  "/swagger-ui.html", "/webjars/**", "/api-docs/**");
    }
}