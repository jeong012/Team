package com.fdproject.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fdproject.domain.UserDTO;
import com.fdproject.service.UserService;

public class CustomAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	// 검증을 위한 구현
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		String userId = authentication.getName();
		String pw = (String)authentication.getCredentials();
		
		UserDTO userDTO = (UserDTO) userService.loadUserByUsername(userId);
		
		if (userDTO == null) {
            throw new BadCredentialsException("userI is not found. userId=" + userId);
        }

        if (!this.passwordEncoder.matches(pw, userDTO.getPassword())) {
            throw new BadCredentialsException("password is not matched");
        }

        return new CustomAuthenticationToken(userId, pw, userDTO.getAuthorities());
	}

	@Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
