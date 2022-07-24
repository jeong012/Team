package com.fdproject.configuration;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
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
	private final UserDetailsService userService;
	//private final WebAccessDeniedHandler webAccessDeniedHandler;
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    	http.csrf().disable();
    	http
            .headers().frameOptions().disable()
            .and()
                .authorizeRequests()
		        .antMatchers("/mypage/**", "/drug/view.do", "/drug/myview.do", "/recipe/view.do").authenticated() // 로그인필요
		        .antMatchers("/admin/**").hasRole("ADMIN") // ROLE_ADMIN 역할을 가지고 있어야함
		        .antMatchers().rememberMe()
		        .anyRequest().permitAll() // 나머지 요청에 대해서는 로그인을 요구하지 않음
            .and()
	            .formLogin()
		        .loginPage("/user/loginForm.do")
		        .usernameParameter("userId") //로그인시 사용할 파라미터 이름
		        .passwordParameter("pw") //로그인시 사용할 파라미터 이름
		        .loginProcessingUrl("/login_proc") //사용자 이름과 암호를 제출할 URL
		        .defaultSuccessUrl("/") //로그인 성공 시 제공할 페이지
		        .failureUrl("/user/login/error.do") //로그인 실패 시 제공할 페이지
            .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/user/logout.do"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true).deleteCookies("JSESSIONID")
            .and()
                .oauth2Login()
                	.userInfoEndpoint()
                		.userService(customOAuth2UserService)
            .and()
            	.defaultSuccessUrl("/user/findByOAuth2User.do")
            .and()
            	//.exceptionHandling().accessDeniedHandler(webAccessDeniedHandler)
            	.exceptionHandling()
            	.authenticationEntryPoint(new CustomAuthenticationEntryPoint());	// 인증되지 않은 사용자가 리소스에 접근하였을 때 수행되는 핸들러
		
		return http.build();
    }
    
    public void configure(WebSecurity web) throws Exception{
    	//web.ignoring().mvcMatchers("/css/**", "/js/**" ,"/img/**");
    	
    	// 정적 자원에 대해서는 Security 설정을 적용하지 않음.
    	web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }
	
	protected void configure(HttpSecurity http) throws Exception{
		http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse());
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		// 회원가입시 비번 암호화
		return new BCryptPasswordEncoder();

	}
	
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		// Spring Security 인증을 위한 AuthenticationManeger 생성
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
	    return authenticationConfiguration.getAuthenticationManager();
	}
	
}