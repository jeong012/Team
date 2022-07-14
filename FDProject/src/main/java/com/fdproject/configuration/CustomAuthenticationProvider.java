//package com.fdproject.configuration;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import com.fdproject.service.UserService;
//import com.fdproject.service.UserServiceImpl;
//
//public class CustomAuthenticationProvider implements AuthenticationProvider {
//
//	@Autowired
//	private UserServiceImpl userServiceImpl;
//	
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//	
//	
//	// 검증을 위한 구현
//	@Override
//	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//
//		String userId = authentication.getName();
//		String pw = (String)authentication.getCredentials();
//		
//		UserServiceImpl user = (UserServiceImpl)user.loadUserByUsername(userId);
//				
//		return null;
//	}
//
//	@Override
//	public boolean supports(Class<?> authentication) {
//		// TODO Auto-generated method stub
//		return false;
//	}
//
//}
