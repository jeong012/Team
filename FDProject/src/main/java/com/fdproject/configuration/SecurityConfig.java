package com.fdproject.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.fdproject.service.CustomOAuth2UserService;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig{

    private final CustomOAuth2UserService customOAuth2UserService;
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	http.csrf().disable();
    	http
            .headers().frameOptions().disable()
            .and()
                .authorizeRequests()
                .antMatchers("/**/**", "/assets/**").permitAll()
		        .mvcMatchers("/**/view/*").hasRole("USER")
		        .mvcMatchers("/admin/**").hasRole("ADMIN")
		        .antMatchers().rememberMe()
		        .anyRequest().authenticated()
            .and()
	            .formLogin()
		        .loginPage("/user/loginForm")
		        .defaultSuccessUrl("/") //로그인 성공 시 제공할 페이지
		        .usernameParameter("userId")
		        .failureUrl("/user/login/error") //로그인 실패 시 제공할 페이지
            .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout.do"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
            .and()
                .oauth2Login()
                	.userInfoEndpoint()
                		.userService(customOAuth2UserService)
            .and()
            	.defaultSuccessUrl("/user/findByUser.do")
            .and()
            	.exceptionHandling()
            	.authenticationEntryPoint(new CustomAuthenticationEntryPoint());	// 인증되지 않은 사용자가 리소스에 접근하였을 때 수행되는 핸들러
		
		return http.build();
    }
	
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	}

	@Bean
	public PasswordEncoder passwordEncoder() { // 회원가입시 비번 암호화
		return new BCryptPasswordEncoder();

	}
}