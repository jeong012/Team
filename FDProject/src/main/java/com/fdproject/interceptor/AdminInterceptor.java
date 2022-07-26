package com.fdproject.interceptor;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fdproject.domain.UserDTO;

public class AdminInterceptor implements HandlerInterceptor {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object, Principal principal, Authentication authentication) throws Exception {
	  
		logger.debug("AdminInterceptor preHandle 작동");
		
		HttpSession session = request.getSession();
		UserDTO userDTO = (UserDTO)authentication.getPrincipal();
		
		logger.debug("AdminInterceptor userDTO >>>>>>>>>>>>>>>>>>>>" + userDTO);
		
		if( userDTO == null) {
			response.sendRedirect("/user/loginForm.do");
			return false;
		}
		
		if( userDTO.getAuthority() != "ROLE_ADMIN") {
			response.sendRedirect("/user/loginForm.do");
			return false;
		}
	  
		return true;
	}
}
