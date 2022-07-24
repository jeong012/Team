package com.fdproject.configuration;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class CustomLoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

	//CustomLoginSuccessHandler는 AuthenticationProvider를 통해 인증이 성공될 경우 처리
	//토큰을 사용하지 않고 세션을 활용
	//성공하여 반환된 Authentication 객체를 SecurityContextHolder의 Contetx에 저장
	//나중에 사용자의 정보를 꺼낼 경우에도 SecurityContextHolder의 context에서 조회
	
	@Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        SecurityContextHolder.getContext().setAuthentication(authentication);
        response.sendRedirect("/"); //인증이 성공될 경우 redirect Url
    }
}
