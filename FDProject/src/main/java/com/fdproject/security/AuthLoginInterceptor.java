//package com.fdproject.security;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import com.fdproject.domain.UserDTO;
//import com.fdproject.service.UserService;
//
//public class AuthLoginInterceptor extends HandlerInterceptorAdapter {
//	
//	@Autowired
//	private UserService userService;//interceptor는 spring container안에 존재하므로 autowired를 할 수 있다.
//	
//	@Override
//	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception {
//		
//		String userId = request.getParameter("userId");
//		String pw = request.getParameter("pw");
//		
////		잘못 된 문장 컨테이너 외의 새로운 userService를 만들었으므로 userService내의 userDao가 null이다.
////		UserService userService = new UserService();
//
//		//외부에서 의존관계가 있는 bean을 가져오는 방법
////		ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());//servletContext가 들어있음 
////		UserService userService = ac.getBean(UserService.class);// ac에 있는 UserService
//		
//		UserDTO users = new UserDTO();
//		users.setUserId(userId);
//		users.setPw(pw);
//		UserDTO authUser = userService.getUserDetail(userId);
//		if(authUser == null) {
//			response.sendRedirect(request.getContextPath()+"/user/login");
//			return false;
//		}
//		//세션 처리
//		HttpSession session = request.getSession(true);
//		session.setAttribute("authUser", authUser);
//		response.sendRedirect(request.getContextPath());
//		
//		return false;//컨트롤러에 로그인 체크 하는 것이 사라지므로 handler mapping에서 정보들이 사라진다. 그러므로 controller에 접근하면 안됨
//	}
//
//}