package com.fdproject.configuration;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
             AuthenticationException authException) throws IOException, ServletException {
		
		// 인증되지 않은 사용자가 ajax로 리소스를 요청할 경우 "Unauthorized" 에러 발생
		if ("XMLHttpRequest".equals(request.getHeader("x-requested-with"))) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
		} else {
			response.sendRedirect("user/loginForm.do");
		}
        
    }

}
