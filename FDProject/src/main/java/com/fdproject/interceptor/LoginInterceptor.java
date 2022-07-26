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
		
		//loginId가 null이 아닌 경우 로그인이 이미 되어있음을 확인하고 return true로 정상적으로 컨트롤러에 요청을 넘김
		if(loginId != null) {
			return true;
		}
		
		//null인 경우 로그인이 되어있는 상태가 아니기 때문에,
		//현재 요청했던 경로를 쿼리스트링과 합쳐 session에 등록하고 로그인 페이지로 리다이렉트
		String destUri = request.getRequestURI();
        String destQuery = request.getQueryString();
        String dest = (destQuery == null) ? destUri : destUri+"?"+destQuery;
        request.getSession().setAttribute("dest", dest);
    
        response.sendRedirect("/user/loginForm.do");
        return false;
		        
		//session.invalidate();
	}
	
}
