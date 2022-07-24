package com.fdproject.configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

//	public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
//        super.setAuthenticationManager(authenticationManager);
//    }
	public CustomAuthenticationFilter(RequestMatcher requiresAuthenticationRequestMatcher) {
        super((AuthenticationManager) requiresAuthenticationRequestMatcher);
    }
	//UserPasswordAuthenticationToken 발급
	//전송이 오면 AuthenticationFilter로 요청이 먼저 오게 되고
	//아이디와 비밀번호를 기반으로 UserPasswordAuthenticationToken을 발급해준다.
//	@Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(request.getParameter("userId"), request.getParameter("pw"));
//        setDetails(request, authRequest);
//        return this.getAuthenticationManager().authenticate(authRequest);
//    }
	@Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        String userId = request.getParameter("userId");
        String pw = request.getParameter("pw");

        return getAuthenticationManager().authenticate(new CustomAuthenticationToken(userId, pw, null));
    }
}
