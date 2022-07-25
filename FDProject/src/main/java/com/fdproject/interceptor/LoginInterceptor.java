package com.fdproject.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import com.fdproject.domain.UserDTO;

public class LoginInterceptor implements HandlerInterceptor {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
	  
		logger.debug("LoginInterceptor preHandle 작동");
		
		String loginId = (String)request.getSession().getAttribute("userId");
		
		if(loginId != null) {
			return true;
		}
		
		String destUri = request.getRequestURI();
        String destQuery = request.getQueryString();
        String dest = (destQuery == null) ? destUri : destUri+"?"+destQuery;
        request.getSession().setAttribute("dest", dest);
    
        response.sendRedirect("/user/loginForm.do");
        return false;
		        
		//session.invalidate();
	}
	
}
